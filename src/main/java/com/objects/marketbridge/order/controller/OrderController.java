package com.objects.marketbridge.order.controller;

import com.objects.marketbridge.common.domain.dto.kakao.KakaoPayReadyRequest;
import com.objects.marketbridge.common.domain.dto.kakao.KakaoPayReadyResponse;
import com.objects.marketbridge.common.infra.entity.AddressEntity;
import com.objects.marketbridge.common.service.KakaoPayService;
import com.objects.marketbridge.member.service.port.MemberRepository;
import com.objects.marketbridge.order.controller.request.CreateOrderRequest;
import com.objects.marketbridge.order.controller.response.CheckoutResponse;
import com.objects.marketbridge.order.dto.CreateOrderDto;
import com.objects.marketbridge.order.service.CreateOrderService;
import com.objects.marketbridge.common.config.KakaoPayConfig;
import com.objects.marketbridge.common.interceptor.ApiResponse;
import com.objects.marketbridge.common.interceptor.error.CustomLogicException;
import com.objects.marketbridge.common.security.annotation.AuthMemberId;
import com.objects.marketbridge.common.infra.entity.MemberEntity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.objects.marketbridge.common.interceptor.error.ErrorCode.SHIPPING_ADDRESS_NOT_REGISTERED;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final MemberRepository memberRepository;
    private final CreateOrderService createOrderService;
    private final KakaoPayConfig kakaoPayConfig;
    private final KakaoPayService kakaoPayService;

    @GetMapping("/orders/checkout")
    public ApiResponse<CheckoutResponse> getCheckout(
            @AuthMemberId Long memberId) {

        MemberEntity member = memberRepository.findByIdWithAddresses(memberId).orElseThrow(EntityNotFoundException::new);
        CheckoutResponse checkoutResponse = createCheckoutResponse(member);

        return ApiResponse.ok(checkoutResponse);
    }

    private CheckoutResponse createCheckoutResponse(MemberEntity member) {

        AddressEntity addressEntity = filterDefaultAddress(member.getAddressEntities());

        return CheckoutResponse.from(addressEntity.getAddressValue());
    }

    private AddressEntity filterDefaultAddress(List<AddressEntity> addressEntities) {

        return addressEntities.stream()
                .filter(AddressEntity::getIsDefault)
                .findFirst()
                .orElseThrow(() -> new CustomLogicException(SHIPPING_ADDRESS_NOT_REGISTERED.getMessage(), SHIPPING_ADDRESS_NOT_REGISTERED));
    }

    @PostMapping("/orders/checkout")
    public ApiResponse<KakaoPayReadyResponse> saveOrder(
            @AuthMemberId Long memberId,
            @Valid @RequestBody CreateOrderRequest request) {

        // 1. kakaoPaymentReadyService 호출
        KakaoPayReadyResponse response = kakaoPayService.ready(createKakaoReadyRequest(request, memberId));
        String tid = response.getTid();

        // 2. 주문 생성
        createOrderService.create(getCreateOrderDto(request, memberId, tid));

        return ApiResponse.ok(response);
    }

    private CreateOrderDto getCreateOrderDto(CreateOrderRequest request, Long memberId, String tid) {

        return CreateOrderDto.fromRequest(request, memberId, tid);
    }
    private KakaoPayReadyRequest createKakaoReadyRequest(CreateOrderRequest request, Long memberId) {

        String cid = KakaoPayConfig.ONE_TIME_CID;
        String cancelUrl = kakaoPayConfig.getCancelUrl();
        String failUrl = kakaoPayConfig.getFailUrl();
        String approvalUrl = kakaoPayConfig.createApprovalUrl("/orders/checkout");

        return request.toKakaoReadyRequest(memberId, cid, approvalUrl, failUrl, cancelUrl);
    }
}
