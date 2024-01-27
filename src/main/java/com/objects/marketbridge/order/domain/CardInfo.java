package com.objects.marketbridge.order.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Embeddable
public class CardInfo {

    @JsonProperty("kakaopay_issuer_corp")
    private String cardIssuerName;

    @JsonProperty("kakaopay_purchase_corp")
    private String cardPurchaseName;

    @JsonProperty("install_month")
    private String cardInstallMonth;

    @Builder
    public CardInfo(String cardIssuerName, String cardPurchaseName, String cardInstallMonth) {
        this.cardIssuerName = cardIssuerName;
        this.cardPurchaseName = cardPurchaseName;
        this.cardInstallMonth = cardInstallMonth;
    }
}
