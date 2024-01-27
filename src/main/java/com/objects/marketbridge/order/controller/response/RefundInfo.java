package com.objects.marketbridge.order.controller.response;

import com.objects.marketbridge.order.dto.RefundDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RefundInfo {

    private Long totalRefundAmount;
    private String refundMethod;
    private LocalDateTime refundProcessedAt; // 환불 일자

    @Builder
    public RefundInfo(Long totalRefundAmount, String refundMethod, LocalDateTime refundProcessedAt) {
        this.totalRefundAmount = totalRefundAmount;
        this.refundMethod = refundMethod;
        this.refundProcessedAt = refundProcessedAt;
    }

    public static RefundInfo of(RefundDto refundDto) {
        return RefundInfo.builder()
                .totalRefundAmount(refundDto.getTotalRefundAmount())
                .refundMethod(refundDto.getRefundMethod())
                .refundProcessedAt(refundDto.getRefundProcessedAt())
                .build();
    }
}
