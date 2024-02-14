package com.objects.marketbridge.payment.infra;

import com.objects.marketbridge.common.dto.KakaoPayApproveRequest;
import com.objects.marketbridge.common.dto.KakaoPayApproveResponse;
import com.objects.marketbridge.common.infra.KakaoPayService;
import com.objects.marketbridge.order.domain.Order;
import com.objects.marketbridge.payment.service.dto.PaymentDto;
import com.objects.marketbridge.payment.service.dto.RefundDto;
import com.objects.marketbridge.payment.service.port.PaymentClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.objects.marketbridge.common.config.KakaoPayConfig.ONE_TIME_CID;

@Service
@RequiredArgsConstructor
public class PaymentClientImpl implements PaymentClient {

    private final KakaoPayService kakaoPayService;

    @Override
    public RefundDto refund(String tid, Integer cancelAmount) {
        return RefundDto.of(kakaoPayService.cancel(tid, cancelAmount));
    }

    @Override
    public PaymentDto payment(Order order, String pgToken) {
        return PaymentDto.of(kakaoPayService.approve(createKakaoRequest(order, pgToken)));
    }

    private KakaoPayApproveRequest createKakaoRequest(Order order, String pgToken) {
        return KakaoPayApproveRequest.builder()
                .pgToken(pgToken)
                .partnerUserId(order.getMember().getId().toString())
                .partnerOrderId(order.getOrderNo())
                .tid(order.getTid())
                .totalAmount(order.getTotalPrice())
                .cid(ONE_TIME_CID)
                .build();
    }
}