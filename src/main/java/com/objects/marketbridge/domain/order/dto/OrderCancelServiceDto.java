package com.objects.marketbridge.domain.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderCancelServiceDto {

    private Long orderId;
    private String cancelReason;

    @Builder
    public OrderCancelServiceDto(Long orderId, String cancelReason) {
        this.orderId = orderId;
        this.cancelReason = cancelReason;
    }
}
