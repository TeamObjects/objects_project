package com.objects.marketbridge.domain.payment.dto;

import com.objects.marketbridge.domain.order.controller.response.TossPaymentsResponse;
import lombok.Builder;

import java.time.LocalDateTime;

public class RefundInfoDto {

    private Long totalRefundAmount;
    private String refundMethod;
    private LocalDateTime refundProcessedAt;

    @Builder
    public RefundInfoDto(Long totalRefundAmount, String refundMethod, LocalDateTime refundProcessedAt) {
        this.totalRefundAmount = totalRefundAmount;
        this.refundMethod = refundMethod;
        this.refundProcessedAt = refundProcessedAt;
    }

    public static RefundInfoDto of(TossPaymentsResponse response) {
        return RefundInfoDto.builder()
                .refundMethod(response.getPaymentMethod())
                .totalRefundAmount(response.getTotalAmount())
                .refundProcessedAt(LocalDateTime.now())
                .build();
    }
}
