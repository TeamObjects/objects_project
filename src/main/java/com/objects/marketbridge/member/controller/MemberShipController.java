package com.objects.marketbridge.member.controller;

import com.objects.marketbridge.common.config.KakaoPayConfig;
import com.objects.marketbridge.common.domain.Membership;
import com.objects.marketbridge.common.dto.*;
import com.objects.marketbridge.common.infra.KakaoPayService;
import com.objects.marketbridge.common.interceptor.ApiResponse;
import com.objects.marketbridge.common.security.annotation.AuthMemberId;
import com.objects.marketbridge.member.controller.request.CreateSubsRequest;
import com.objects.marketbridge.member.dto.CreateSubsDto;
import com.objects.marketbridge.member.service.MemberShipService;
import com.objects.marketbridge.member.service.port.MembershipRepository;
import com.objects.marketbridge.payment.domain.Amount;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.objects.marketbridge.common.config.KakaoPayConfig.SUBS_CID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberShipController {

    private final MemberShipService memberShipService;
    private final KakaoPayConfig kakaoPayConfig;
    private final KakaoPayService kakaoPayService;
    private final MembershipRepository membershipRepository;

    //TODO uri명명
    
    @GetMapping("/membership/{id}")
    public void changeMembership(@PathVariable Long id){
        memberShipService.changeMemberShip(id);
    }

    @PostMapping("/membership/subsMember")
    public ApiResponse<KakaoPayReadyResponse> saveOrder( // 받을때 cid를 정기결제용
                                                         @AuthMemberId Long memberId,
                                                         @Valid @RequestBody CreateSubsRequest request) {

        String subsOrderNo = UUID.randomUUID().toString();
        // 1. kakaoSubsPaymentReadyService 호출
        KakaoPayReadyResponse response = kakaoPayService.ready(createKakaoReadyRequest(request, memberId,subsOrderNo));
        log.info("response.getTid , {}", response.getTid());
        log.info("response.getNextRedirectPcUrl , {}", response.getNextRedirectPcUrl());
        log.info("response.getNextRedirectMobileUrl , {}", response.getNextRedirectMobileUrl());
        log.info("response.getNextRedirectAppUrl , {}", response.getNextRedirectAppUrl());
        log.info("response.getCreatedAt , {}", response.getCreatedAt());

        String tid = response.getTid();

        // 2. 정보 저장
        Amount amount = Amount.builder().totalAmount(request.getPrice()).build();
        memberShipService.savePayReadyData(getCreateOrderDto(amount, memberId, tid ,subsOrderNo));

        return ApiResponse.ok(response);
    }


    @GetMapping("/membership/kakao-pay/approval/{orderNo}")
    public ApiResponse<KakaoPayApproveResponse> kakaoPaymentApproved(
            @RequestParam(name = "pg_token") String pgToken,
            @PathVariable (name = "orderNo") String orderNo){

        //TODO 성능최적화에 필요한 멤버랑 멤버십 fetchjoin으로 가져오는 JPQL 쿼리메서드 필요
        Membership membership = membershipRepository.findBySubsOrderNo(orderNo);
        KakaoPayApproveResponse response = kakaoPayService.approve(createKakaoRequest(membership, pgToken));

        // 2. Payment 생성 및 OrderDetails 업데이트
        memberShipService.saveAprrovalResponse(response);

        // 3.
        // TODO : 1) 판매자 금액 추가(실제입금은 배치로 들어가겠지만, 우선 어딘가에 판매자의 돈이 올라갔음을 저장해놔야함)
        // TODO : 6) 결제 실패시 어떻게 처리?

        return ApiResponse.ok(response);
    }

    //정기결제 2회차
    @PostMapping("/membership/online/v1/payment/subscription")
    public ApiResponse<KakaoPayApproveResponse> kakaoPaySubsPayment()
    {
        //TODO 배치로 DB에서 필요한 값을 받아서 처리를 해야함
        Membership membership = membershipRepository.findById(1L);
        KakaoPayApproveResponse response = kakaoPayService.subsApprove(createSubsApprove(membership));
        memberShipService.saveAprrovalResponse(response);

        return ApiResponse.ok(response);
    }


    private KakaoPaySubsApproveRequest createSubsApprove(Membership membership){
        return KakaoPaySubsApproveRequest.builder()
                .cid(membership.getCid())
                .sid(membership.getSid())
                .partnerOrderId(membership.getPartnerOrderId())
                .partnerUserId(membership.getPartnerUserId())
                .itemName(membership.getItemName())
                .quantity(membership.getQuantity())
                .totalAmount(membership.getAmount().getTotalAmount())
                .taxFreeAmount(membership.getAmount().getTaxFreeAmount())
                .build();
    }

    private KakaoPayApproveRequest createKakaoRequest(Membership membership, String pgToken) {
        return KakaoPayApproveRequest.builder()
                .pgToken(pgToken)
                .partnerOrderId(membership.getSubsOrderNo())
                .partnerUserId(membership.getMember().getId().toString())
                .tid(membership.getTid())
                .totalAmount(membership.getAmount().getTotalAmount())
                .cid(SUBS_CID)
                .build();
    }

    // 정기결제 Request 생성 메서드
    private CreateSubsDto getCreateOrderDto(Amount amount, Long memberId, String tid, String subsOrderNo) {
        return CreateSubsDto.of(amount, memberId, tid ,subsOrderNo);
    }

    private KakaoPayReadyRequest createKakaoReadyRequest(CreateSubsRequest request, Long memberId, String subsOrderNo) {
        String cid = SUBS_CID;
        String cancelUrl = kakaoPayConfig.getRedirectCancelUrl();
        String failUrl = kakaoPayConfig.getRedirectFailUrl();
        String approvalUrl = kakaoPayConfig.createApprovalUrl("/membership");

        log.info("cancelUrl , {}" ,cancelUrl);
        log.info("failUrl , {}" ,failUrl);
        log.info("approvalUrl , {}" ,approvalUrl);
        return request.toKakaoReadyRequest(memberId,subsOrderNo, cid, approvalUrl, failUrl, cancelUrl);
    }
}