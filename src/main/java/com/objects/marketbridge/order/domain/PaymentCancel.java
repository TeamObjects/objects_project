package com.objects.marketbridge.order.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCancel {
    private Long cancelAmount;
    private String paymentCancelReason;
    private String canceledAt;
    private String transactionKey;
}
