package com.objects.marketbridge.common.infra.entity;

import com.objects.marketbridge.order.domain.Amount;
import com.objects.marketbridge.order.domain.CardInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    private String orderNo;
    private String paymentMethod; // CARD, MONEY
    private String tid;

    // 카드 결제
    @Embedded
    private CardInfo cardInfo;

    // 결제 금액 정보
    @Embedded
    private Amount amount;

    @Builder
    public PaymentEntity(OrderEntity orderEntity, String orderNo, String paymentMethod, String tid, CardInfo cardInfo, Amount amount) {
        this.orderEntity = orderEntity;
        this.orderNo = orderNo;
        this.paymentMethod = paymentMethod;
        this.tid = tid;
        this.cardInfo = cardInfo;
        this.amount = amount;
    }

    public static PaymentEntity create(String orderNo, String paymentMethod, String tid, CardInfo cardInfo, Amount amount) {
        return com.objects.marketbridge.common.infra.entity.PaymentEntity.builder()
                .orderNo(orderNo)
                .paymentMethod(paymentMethod)
                .tid(tid)
                .cardInfo(cardInfo)
                .amount(amount)
                .build();
    }

    // Order와 연관관계 매핑 해주는 메서드 (단방향)
    public void linkOrder(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }
}
