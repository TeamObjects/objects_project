package com.objects.marketbridge.domain.payment.domain;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Embeddable
public class Card {
    String cardIssuerCode; // 카드 발급사 숫자 코드
    String cardNo; // 카드 번호(일부는 마스킹되어있음)
    Long cardInstallmentPlanMonths; // 할부 개월 수(일시불=0)
    String cardApproveNo; // 카드사 승인 번호

    @Builder
    public Card(String cardIssuerCode, String cardNo, Long cardInstallmentPlanMonths, String cardApproveNo) {
        this.cardIssuerCode = cardIssuerCode;
        this.cardNo = cardNo;
        this.cardInstallmentPlanMonths = cardInstallmentPlanMonths;
        this.cardApproveNo = cardApproveNo;
    }
}
