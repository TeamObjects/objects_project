package com.objects.marketbridge.domain.payment.domain;

import com.objects.marketbridge.model.BaseEntity;
import com.objects.marketbridge.domain.order.entity.ProdOrder;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "prod_order_id")
    private ProdOrder prodOrder;

    private String orderNo;
    private String paymentType;
    private String paymentMethod; // CARD, TRANSFER, VIRTUAL
    private String paymentKey;
    private String paymentStatus;
    private String refundStatus;

    // 카드 결제
    @Embedded
    private Card card;

    //가상 계좌 결제
    @Embedded
    private VirtualAccount virtualAccount;

    //계좌 이체 결제
    @Embedded
    private Transfer transfer;

    @Builder
    public Payment(ProdOrder prodOrder, String orderNo, String paymentType, String paymentMethod, String paymentKey, String paymentStatus, String refundStatus,  Card card, VirtualAccount virtualAccount, Transfer transfer) {
        this.prodOrder = prodOrder;
        this.orderNo = orderNo;
        this.paymentType = paymentType;
        this.paymentMethod = paymentMethod;
        this.paymentKey = paymentKey;
        this.paymentStatus = paymentStatus;
        this.refundStatus = refundStatus;
        this.card = card;
        this.virtualAccount = virtualAccount;
        this.transfer = transfer;
    }

    public static Payment create(String orderNo, String paymentType, String paymentMethod, String paymentKey, String paymentStatus, String refundStatus,  Card card, VirtualAccount virtualAccount, Transfer transfer) {
        return Payment.builder()
                .orderNo(orderNo)
                .paymentType(paymentType)
                .paymentMethod(paymentMethod)
                .paymentKey(paymentKey)
                .paymentStatus(paymentStatus)
                .refundStatus(refundStatus)
                .card(card)
                .virtualAccount(virtualAccount)
                .transfer(transfer)
                .build();
    }

    // ProdOrder와 연관관계 매핑 해주는 메서드 (단방향)
    public void linkProdOrder(ProdOrder prodOrder) {
        this.prodOrder = prodOrder;
    }
}
