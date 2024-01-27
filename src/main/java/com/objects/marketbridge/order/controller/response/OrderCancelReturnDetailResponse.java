package com.objects.marketbridge.order.controller.response;

import com.objects.marketbridge.common.infra.entity.OrderEntity;
import com.objects.marketbridge.common.infra.entity.OrderDetailEntity;
import com.objects.marketbridge.common.infra.entity.PaymentEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderCancelReturnDetailResponse {

    private LocalDateTime orderDate;
    private LocalDateTime cancelDate;
    private String orderNo;
    private String cancelReason;
    private List<ProductResponse> productResponseList;
    private CancelRefundInfoResponse cancelRefundInfoResponse;


    @Builder
    private OrderCancelReturnDetailResponse(LocalDateTime orderDate, String orderNo, List<ProductResponse> productResponseList, LocalDateTime cancelDate, String cancelReason, CancelRefundInfoResponse cancelRefundInfoResponse) {
        this.orderDate = orderDate;
        this.orderNo = orderNo;
        this.productResponseList = productResponseList;
        this.cancelDate = cancelDate;
        this.cancelReason = cancelReason;
        this.cancelRefundInfoResponse = cancelRefundInfoResponse;
    }

    public static OrderCancelReturnDetailResponse of(OrderEntity orderEntity, List<OrderDetailEntity> orderDetailEntities, PaymentEntity paymentEntity) {
        return OrderCancelReturnDetailResponse.builder()
                .orderDate(orderEntity.getCreatedAt())
                .cancelDate(orderEntity.getUpdatedAt())
                .orderNo(orderEntity.getOrderNo())
                .cancelReason(orderDetailEntities.get(0).getReason())
                .productResponseList(
                        orderDetailEntities.stream()
                        .map(ProductResponse::of)
                        .toList()
                )
                .cancelRefundInfoResponse(
                        CancelRefundInfoResponse.builder()
                                .refundFee(0L)
                                .deliveryFee(0L)
                                .discountPrice(
                                        orderDetailEntities.stream()
                                        .mapToLong(od -> od.getCoupon().getPrice())
                                        .sum()
                                )
                                .totalPrice(orderDetailEntities.stream()
                                        .mapToLong(OrderDetailEntity::getPrice)
                                        .sum()
                                )
                                .build()
                )
                .build();
    }

}
