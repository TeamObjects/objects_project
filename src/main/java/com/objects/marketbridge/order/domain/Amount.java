package com.objects.marketbridge.order.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class Amount {

    @JsonProperty("total")
    private Long totalAmount;

    @JsonProperty("discount")
    private Long discountAmount;

    @Builder
    public Amount(Long totalAmount, Long discountAmount) {
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
    }
}
