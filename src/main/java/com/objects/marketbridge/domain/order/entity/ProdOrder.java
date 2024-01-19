package com.objects.marketbridge.domain.order.entity;

import com.objects.marketbridge.domain.model.Address;
import com.objects.marketbridge.domain.model.BaseEntity;
import com.objects.marketbridge.domain.model.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProdOrder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    private String orderName;

    private String orderNo;

    private Long totalUsedCouponPrice; // 총 사용된 쿠폰 금액

    private Long totalPrice; // 찐 최종 주문 금액

    private Long realPrice; // 쿠폰, 포인트사용 뺀 진짜 결제된 금액

    private Long usedPoint; // 구매하는데 사용한 포인트

    @OneToMany(mappedBy = "prodOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdOrderDetail> prodOrderDetails = new ArrayList<>();

    @Builder
    public ProdOrder(Member member, Address address, String orderName, String orderNo, Long realPrice, Long totalPrice, Long totalUsedCouponPrice, Long usedPoint) {
        this.member = member;
        this.address = address;
        this.orderName = orderName;
        this.orderNo = orderNo;
        this.realPrice = realPrice;
        this.totalPrice = totalPrice;
        this.totalUsedCouponPrice = totalUsedCouponPrice;
        this.usedPoint = usedPoint;
    }

    public void addOrderDetail(ProdOrderDetail prodOrderDetail) {
        prodOrderDetails.add(prodOrderDetail);
        prodOrderDetail.setOrder(this);
    }

    public static ProdOrder create(Member member, Address address, String orderName, String orderNo, Long totalPrice, Long realPrice, Long totalUsedCouponPrice){
        return ProdOrder.builder()
                .member(member)
                .address(address)
                .orderName(orderName)
                .orderNo(orderNo)
                .totalPrice(totalPrice)
                .realPrice(realPrice)
                .totalUsedCouponPrice(totalUsedCouponPrice)
                .build();
    }

    //== 비즈니스 로직==//
    public void cancel(String reason, String statusCode, LocalDateTime cancelDateTime) {
        changeUpdateAt(cancelDateTime);
        prodOrderDetails.forEach(prodOrderDetail -> prodOrderDetail.cancel(reason, statusCode));
    }

    public void returnCoupon() {
        prodOrderDetails.forEach(ProdOrderDetail::returnCoupon);
    }
}
