package com.objects.marketbridge.domain.order.entity;

import com.objects.marketbridge.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProdOrder extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "prod_order_id")
    private Long id;

    // TODO
    private Long userId;
    // TODO
    private Long addressId;

    private String statusCode; // 00, DELIVERED, CANCEL, RETURN, EXCHANGE

    private Long totalPrice;

    private Integer pointRate; // 적립율

    private Integer savedPoint;

    private LocalDateTime deliveredDate;

    @Builder
    private ProdOrder(Long userId, Long addressId, String statusCode, Long totalPrice, Integer pointRate, Integer savedPoint, LocalDateTime deliveredDate) {
        this.userId = userId;
        this.addressId = addressId;
        this.statusCode = statusCode;
        this.totalPrice = totalPrice;
        this.pointRate = pointRate;
        this.savedPoint = savedPoint;
        this.deliveredDate = deliveredDate;
    }
}
