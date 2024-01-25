package com.objects.marketbridge.domain.order.dto;

import com.objects.marketbridge.domain.order.entity.ProductValue;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateOrderDto {
    private Long memberId;
    private Long addressId;
    private String orderName;
    private String orderNo;
    private Long totalOrderPrice;
    private Long realOrderPrice;
    private List<ProductValue> productValues;



    @Builder
    public CreateOrderDto(Long memberId, Long addressId, String orderName, String orderNo, Long totalOrderPrice, Long realOrderPrice,   List<ProductValue> productValues) {
        this.memberId = memberId;
        this.addressId = addressId;
        this.orderName = orderName;
        this.orderNo = orderNo;
        this.totalOrderPrice = totalOrderPrice;
        this.realOrderPrice = realOrderPrice;
        this.productValues = productValues;
    }
}
