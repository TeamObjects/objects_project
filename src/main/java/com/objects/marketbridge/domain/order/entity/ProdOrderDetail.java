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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProdOrderDetail extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "prod_order_detail_id")
    private Long id;

    // TODO
    private Long orderId;

    // TODO
    private Long productId;

    private Long price;

    @Builder
    private ProdOrderDetail(Long orderId, Long productId, Long price) {
        this.orderId = orderId;
        this.productId = productId;
        this.price = price;
    }
}
