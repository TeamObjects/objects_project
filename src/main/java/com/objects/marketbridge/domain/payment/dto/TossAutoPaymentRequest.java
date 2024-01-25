package com.objects.marketbridge.domain.payment.dto;

import lombok.Getter;
import java.util.UUID;

@Getter
public class TossAutoPaymentRequest {
    private String customerKey;
    private String cardNumber;
    private String cardExpirationYear;
    private String cardExpirationMonth;
    private String customerIdentityNumber;

    public TossAutoPaymentRequest() {
    }

    public TossAutoPaymentRequest(String customerKey, String cardNumber, String cardExpirationYear, String cardExpirationMonth, String customerIdentityNumber) {
        this.customerKey = generateUUID();
        this.cardNumber = "4738536288989019";
        this.cardExpirationYear = "30";
        this.cardExpirationMonth = "08";
        this.customerIdentityNumber = "930924";
    }


    private String generateUUID (){
        // 랜덤 UUID 생성
        UUID randomUuid = UUID.randomUUID();

        return randomUuid.toString();
    }
}
