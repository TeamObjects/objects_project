package com.objects.marketbridge.domain;

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
    @GeneratedValue
    @Column(name = "payment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prod_order_id")
    private ProdOrder orderId;

    //TODO : receipt가 테이블인지 아니면 그냥 텍스트인지 의문
    private String receiptId;

    @Enumerated(EnumType.STRING)
    private PayMethod payMethod; // CARD, CHECK, BANK, AUTO

    //카드번호, 계좌번호
    private String payNum;

    // 카드사
    private String pg;

    //TODO : 서드 파티 결제 API가 보내주는 컬럼들 추가해야함


    @Enumerated(EnumType.STRING)
    private StatusCodeType status;

    @Builder
    private Payment(ProdOrder orderId, String receiptId, PayMethod payMethod, String payNum, String pg, StatusCodeType status) {
        this.orderId = orderId;
        this.receiptId = receiptId;
        this.payMethod = payMethod;
        this.payNum = payNum;
        this.pg = pg;
        this.status = status;
    }
}
