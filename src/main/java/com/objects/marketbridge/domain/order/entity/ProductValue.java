package com.objects.marketbridge.domain.order.entity;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class ProductValue {
    Long productId;
    Long couponId;
    Long quantity;
    String deliveredDate;

    @Builder
    public ProductValue(Long productId, Long couponId, Long quantity, String deliveredDate) {
        this.productId = productId;
        this.couponId = couponId;
        this.quantity = quantity;
        this.deliveredDate = deliveredDate;
    }
}
