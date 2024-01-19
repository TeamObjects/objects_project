package com.objects.marketbridge.domain.order.controller.response;

import com.objects.marketbridge.domain.model.AddressValue;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CheckoutResponse {
    // 이름, 배송지, 연락처
    AddressValue address;

    @Builder
    public CheckoutResponse(AddressValue address) {
        this.address = address;
    }


    public static CheckoutResponse from(AddressValue address) {
        return CheckoutResponse.builder()
                .address(address)
                .build();
    }

}
