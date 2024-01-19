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
public class TossPaymentsResponse {

    private String orderId;
    private String paymentType; // 일반결제, 브랜드페이
    private String paymentMethod; // CARD, TRANSFER, VIRTUAL
    private Long totalAmount;
    private String paymentKey;
    private String paymentStatus;
    private String refundStatus;
    private String bankCode;
    private String orderName;

    // 결제 취소에 대한 값
    private List<PaymentCancel> cancels;

    // 카드 결제
    private Card card;

    // 가상 계좌 결제
    private VirtualAccount virtualAccount;

    // 계좌 이체 결제
    private Transfer transfer;

    @Builder
    public TossPaymentsResponse(String orderId,  String paymentType, String paymentMethod, Long totalAmount, String paymentKey, String paymentStatus, String refundStatus, String bankCode, String orderName, List<PaymentCancel> cancels, Card card, VirtualAccount virtualAccount, Transfer transfer) {
        this.orderId = orderId;
        this.paymentType = paymentType;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
        this.paymentKey = paymentKey;
        this.paymentStatus = paymentStatus;
        this.refundStatus = refundStatus;
        this.bankCode = bankCode;
        this.orderName = orderName;
        this.cancels = cancels;
        this.card = card;
        this.virtualAccount = virtualAccount;
        this.transfer = transfer;
    }
}
