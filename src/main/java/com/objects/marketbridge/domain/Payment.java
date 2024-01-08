package com.objects.marketbridge.domain;

import com.objects.marketbridge.domain.order.entity.StatusCodeType;
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

    // TODO
    private Long orderId;

    private String receiptId;

    @Enumerated(EnumType.STRING)
    private PayMethod payMethod; // CARD, CHECK, BANK, AUTO

    //카드번호, 계좌번호
    private String payNum;

    // 카드사
    private String pg;

    @Enumerated(EnumType.STRING)
    private StatusCodeType status;

    @Builder
    private Payment(Long orderId, String receiptId, PayMethod payMethod, String payNum, String pg, StatusCodeType status) {
        this.orderId = orderId;
        this.receiptId = receiptId;
        this.payMethod = payMethod;
        this.payNum = payNum;
        this.pg = pg;
        this.status = status;
    }
}
