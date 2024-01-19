package com.objects.marketbridge.domain.model;

import com.objects.marketbridge.domain.order.entity.ProdOrderDetail;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    private String deliveryType; // NORMAL, EXCHANGE, RETURN // column이름이랑 다름

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prod_order_detail_id")
    private ProdOrderDetail prodOrderDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    private String carrier;

    private String trackingNo;

    private String statusCode;

    private LocalDateTime shipDate;

    private LocalDateTime deliveredDate;

    @Builder
    private Delivery(String deliveryType, ProdOrderDetail prodOrderDetail, Seller seller, Address address, String carrier, String trackingNo, String statusCode, LocalDateTime shipDate, LocalDateTime deliveredDate) {
        this.deliveryType = deliveryType;
        this.prodOrderDetail = prodOrderDetail;
        this.seller = seller;
        this.address = address;
        this.carrier = carrier;
        this.trackingNo = trackingNo;
        this.statusCode = statusCode;
        this.shipDate = shipDate;
        this.deliveredDate = deliveredDate;
    }
}
