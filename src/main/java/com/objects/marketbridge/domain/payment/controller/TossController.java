package com.objects.marketbridge.domain.payment.controller;

import com.objects.marketbridge.domain.order.controller.response.TossPaymentsResponse;
import com.objects.marketbridge.domain.order.service.TossApiService;
import com.objects.marketbridge.domain.payment.dto.TossConfirmRequest;
import com.objects.marketbridge.domain.payment.service.CreatePaymentService;
import com.objects.marketbridge.global.common.ApiResponse;
import com.objects.marketbridge.global.security.annotation.AuthMemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TossController {

    private final CreatePaymentService createPaymentService;
    private final TossApiService tossApiService;

    @GetMapping("/payments/toss/success")
    public ApiResponse<TossPaymentsResponse> tossPaymentSuccess(
            @AuthMemberId Long memberId,
            @RequestParam String paymentKey,
            @RequestParam(name = "orderId") String orderNo,
            @RequestParam(name ="amount") Long totalOrderPrice) {


        // 1. 결제 요청
        TossPaymentsResponse tossPaymentsResponse =
                tossApiService.requestPaymentAccept(new TossConfirmRequest(paymentKey, orderNo, totalOrderPrice));

        // 2. Payment 생성
        createPaymentService.create(tossPaymentsResponse);

        // 3.
        // TODO : 1) 판매자 금액 추가(실제입금은 배치로 들어가겠지만, 우선 어딘가에 판매자의 돈이 올라갔음을 저장해놔야함)
        //  TODO : 2) 재고 감소
        // TODO : 3) 쿠폰 사용시 쿠폰 감소
        // TODO : 4) 배송 엔티티 생성 되어야함
        // TODO  :5) 주문 상태 업데이트
        // TODO : 6) 결제 실패시 어떻게 처리?

        return ApiResponse.ok(tossPaymentsResponse);

    }

    @GetMapping("/payments/toss/fail")
    public ApiResponse<TossPaymentsResponse> tossPaymentFail(
            @RequestParam String paymentKey,
            @RequestParam(name = "orderId") String orderNo,
            @RequestParam Long amount) {


        return null;

    }
}
