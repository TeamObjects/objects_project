package com.objects.marketbridge.common.infra.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.objects.marketbridge.common.domain.enums.StatusCodeType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDetailEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private CouponEntity coupon;

    private Long quantity;

    private String orderNo;

    private Long price;

    private String statusCode;

    private LocalDateTime deliveredDate;

    private String reason;

    private String tid;

    private LocalDateTime cancelledAt;

    @Builder
    public OrderDetailEntity(OrderEntity orderEntity, String orderNo, String tid, ProductEntity product, CouponEntity coupon, Long quantity, Long price, String statusCode, LocalDateTime deliveredDate, String reason, LocalDateTime cancelledAt) {
        this.orderNo = orderNo;
        this.tid = tid;
        this.orderEntity = orderEntity;
        this.product = product;
        this.coupon = coupon;
        this.quantity = quantity;
        this.price = price;
        this.statusCode = statusCode;
        this.deliveredDate = deliveredDate;
        this.reason = reason;
        this.cancelledAt = cancelledAt;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    public void changeStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public static OrderDetailEntity create(String tid, OrderEntity orderEntity, ProductEntity product, String orderNo, CouponEntity coupon, Long quantity, Long price, String statusCode) {

        return OrderDetailEntity.builder()
                .tid(tid)
                .order(orderEntity)
                .orderNo(orderNo)
                .product(product)
                .coupon(coupon)
                .quantity(quantity)
                .price(price)
                .statusCode(statusCode)
                .build();
    }

    public void cancel(String reason, String statusCode) {
        if (Objects.equals(statusCode, DELIVERY_COMPLETED.getCode())) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.statusCode = statusCode;
        this.reason = reason;
        this.product.increase(quantity);
    }

    public void returnCoupon() {
        coupon.returnCoupon();
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }
}
