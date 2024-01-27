package com.objects.marketbridge.order.dto;

import com.objects.marketbridge.order.controller.response.ProductInfoResponse;
import com.objects.marketbridge.common.infra.entity.OrderDetailEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class OrderReturnResponse {

    private List<ProductInfoResponse> productResponses;
    private ReturnRefundInfoResponse returnRefundInfoResponse;

    @Builder
    private OrderReturnResponse(List<ProductInfoResponse> productResponses, ReturnRefundInfoResponse returnRefundInfoResponse) {
        this.productResponses = productResponses;
        this.returnRefundInfoResponse = returnRefundInfoResponse;
    }

    public static OrderReturnResponse of(List<OrderDetailEntity> orderDetailEntities) {
        return OrderReturnResponse.builder()
                .productResponses(orderDetailEntities.stream()
                        .map(ProductInfoResponse::of)
                        .collect(Collectors.toList())
                )
                .returnRefundInfoResponse(
                        ReturnRefundInfoResponse.builder()
                                .productPrice(
                                        orderDetailEntities.stream()
                                                .mapToLong(OrderDetailEntity::getPrice)
                                                .sum()
                                )
                                .deliveryFee(0L)
                                .returnFee(0L)
                                .build()
                )
                .build();

    }
}
