package com.objects.marketbridge.domain.order.controller.request;

import com.objects.marketbridge.domain.order.dto.CreateOrderDto;
import com.objects.marketbridge.domain.order.entity.ProductValue;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CheckoutRequest {

    @NotNull
    private String orderId;

    @NotNull
    private Long amount;

    @NotNull
    private Long addressId;

    @NotNull
    private String orderName;

    @NotNull
    private List<ProductValue> productValues;

    private String successUrl;

    private String failUrl;

    @Builder
    public CheckoutRequest(String orderId, Long amount, Long addressId, String orderName, List<ProductValue> productValues, String successUrl, String failUrl) {
        this.orderId = orderId;
        this.amount = amount;
        this.addressId = addressId;
        this.orderName = orderName;
        this.productValues = productValues;
        this.successUrl = successUrl;
        this.failUrl = failUrl;
    }

    public CreateOrderDto toDto(Long memberId) {
        return CreateOrderDto.builder()
                .memberId(memberId)
                .addressId(addressId)
                .orderName(orderName)
                .orderNo(orderId)
                .totalOrderPrice(amount)
                .productValues(productValues)
                .build();
    }
}
