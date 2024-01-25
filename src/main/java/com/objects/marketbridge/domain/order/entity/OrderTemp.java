package com.objects.marketbridge.domain.order.entity;

import com.objects.marketbridge.domain.order.controller.request.CheckoutRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderTemp {
    @Column(name="order_temp_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String orderNo;

    Long amount;

    Long addressId;

    String orderName;

    @Embedded
    ProductValue productValue;


    @Builder
    public OrderTemp(String orderNo, Long amount, Long addressId, String orderName, ProductValue productValue) {
        this.orderNo = orderNo;
        this.amount = amount;
        this.addressId = addressId;
        this.orderName = orderName;
        this.productValue = productValue;
    }

    public static OrderTemp from(CheckoutRequest request) {
        return OrderTemp.builder()
                .orderNo(request.getOrderId())
                .amount(request.getAmount())
                .orderName(request.getOrderName())
                .addressId(request.getAddressId()).build();
    }

    public CheckoutRequest toDto() {
        return CheckoutRequest.builder()
                .orderId(orderNo)
                .amount(amount)
                .orderName(orderName)
                .addressId(addressId)
                .build();
    }
}
