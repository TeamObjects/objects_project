package com.objects.marketbridge.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProdOrderDetail extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "prod_order_detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prod_order_id")
    private ProdOrder prodOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    private Integer quantity;

    private Integer price;

    private String statusCode;

    private LocalDateTime deliveredDate;

    private Integer usedPoint;

    private String reason;

    private LocalDateTime cancelledAt;

    @Builder
    private ProdOrderDetail(ProdOrder prodOrder, Product product, Coupon coupon, Integer quantity, Integer price, String statusCode, LocalDateTime deliveredDate, Integer usedPoint, String reason, LocalDateTime cancelledAt) {
        this.prodOrder = prodOrder;
        this.product = product;
        this.coupon = coupon;
        this.quantity = quantity;
        this.price = price;
        this.statusCode = statusCode;
        this.deliveredDate = deliveredDate;
        this.usedPoint = usedPoint;
        this.reason = reason;
        this.cancelledAt = cancelledAt;
    }
}