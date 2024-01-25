package com.objects.marketbridge.domain.payment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RefundDto {

    private Long totalRefundAmount;
    private String refundMethod;
    private LocalDateTime refundProcessedAt; // 환불 일자

    @Builder
    public RefundDto(Long totalRefundAmount, String refundMethod, LocalDateTime refundProcessedAt) {
        this.totalRefundAmount = totalRefundAmount;
        this.refundMethod = refundMethod;
        this.refundProcessedAt = refundProcessedAt;
    }

    public static RefundDto of(RefundInfoDto refundInfoDto) {
        return null;
    }
}
