package com.objects.marketbridge.domain.payment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Transfer {
    private String trBankCode;

    @Builder
    public Transfer(String bankCode) {
        this.trBankCode = trBankCode;
    }
}
