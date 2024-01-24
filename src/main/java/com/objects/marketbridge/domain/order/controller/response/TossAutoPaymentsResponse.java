package com.objects.marketbridge.domain.order.controller.response;

import com.objects.marketbridge.domain.payment.domain.Card;
import com.objects.marketbridge.domain.payment.domain.PaymentCancel;
import com.objects.marketbridge.domain.payment.domain.Transfer;
import com.objects.marketbridge.domain.payment.domain.VirtualAccount;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TossAutoPaymentsResponse {

    private String mId;
    private String customerKey; // 일반결제, 브랜드페이
    private String authenticatedAt; // CARD, TRANSFER, VIRTUAL
    private String method;
    private String billingKey;

    @Builder
    public TossAutoPaymentsResponse(String mId, String customerKey, String authenticatedAt, String method, String billingKey) {
        this.mId = mId;
        this.customerKey = customerKey;
        this.authenticatedAt = authenticatedAt;
        this.method = method;
        this.billingKey = billingKey;
    }
}
