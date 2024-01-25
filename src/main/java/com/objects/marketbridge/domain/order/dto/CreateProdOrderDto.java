package com.objects.marketbridge.domain.order.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateProdOrderDto {
    private Long memberId;
    private Long addressId;
    private String orderName;
    private String orderNo;
    private Long totalSavedPoint;
    private Long totalUsedCoupon;
    private Long membershipDiscountPrice;
    private Long totalOrderPrice;
    private Long realOrderPrice;

    @Builder
    public CreateProdOrderDto(Long memberId, Long addressId, String orderName, String orderNo, Long totalSavedPoint, Long totalUsedCoupon, Long membershipDiscountPrice, Long totalOrderPrice, Long realOrderPrice) {
        this.memberId = memberId;
        this.addressId = addressId;
        this.orderName = orderName;
        this.orderNo = orderNo;
        this.totalSavedPoint = totalSavedPoint;
        this.totalUsedCoupon = totalUsedCoupon;
        this.membershipDiscountPrice = membershipDiscountPrice;
        this.totalOrderPrice = totalOrderPrice;
        this.realOrderPrice = realOrderPrice;
    }
}
