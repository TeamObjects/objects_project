package com.objects.marketbridge.domain.payment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TossConfirmRequest {
    private final String paymentKey;
    private final String orderNo;
    private final Long amount;

    @Builder
    public TossConfirmRequest(String paymentKey, String orderNo, Long amount) {
        this.paymentKey = paymentKey;
        this.orderNo = orderNo;
        this.amount = amount;
    }
}
