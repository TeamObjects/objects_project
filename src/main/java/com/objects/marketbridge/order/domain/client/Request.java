package com.objects.marketbridge.order.domain.client;

import lombok.Builder;

public class Request {

    private String cancelReason;
    private Long cancelAmount;

    @Builder
    public Request(String cancelReason, Long cancelAmount) {
        this.cancelReason = cancelReason;
        this.cancelAmount = cancelAmount;
    }

    public static Request create(String cancelReason, Long cancelAmount) {
        return Request.builder()
                .cancelAmount(cancelAmount)
                .cancelReason(cancelReason)
                .build();
    }
}
