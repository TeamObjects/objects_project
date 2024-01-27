package com.objects.marketbridge.order.controller.response;

import com.objects.marketbridge.common.infra.entity.OrderEntity;
import com.objects.marketbridge.common.infra.entity.OrderDetailEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class OrderCancelResponse {

    private List<ProductInfoResponse> productResponses;
    private CancelRefundInfoResponse cancelRefundInfoResponse;

    @Builder
    private OrderCancelResponse(List<ProductInfoResponse> productResponses, CancelRefundInfoResponse cancelRefundInfoResponse) {
        this.productResponses = productResponses;
        this.cancelRefundInfoResponse = cancelRefundInfoResponse;
    }

    public static OrderCancelResponse of(List<OrderDetailEntity> orderDetailEntities, OrderEntity orderEntity) {
        return OrderCancelResponse.builder()
                .productResponses(orderDetailEntities.stream()
                        .map(ProductInfoResponse::of)
                        .collect(Collectors.toList())
                )
                .cancelRefundInfoResponse(CancelRefundInfoResponse.builder()
                        .refundFee(0L)
                        .deliveryFee(0L) // TODO 주문에서 배송비 가져오기
                        .discountPrice(orderEntity.getTotalUsedCouponPrice()) // TODO 할인금액 쿠폰만 가능?
                        .totalPrice(orderDetailEntities.stream()
                                .mapToLong(OrderDetailEntity::getPrice)
                                .sum()
                        )
                        .build())
                .build();
    }

}
