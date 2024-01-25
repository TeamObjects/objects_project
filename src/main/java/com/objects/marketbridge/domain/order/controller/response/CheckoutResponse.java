package com.objects.marketbridge.domain.order.controller.response;

import com.objects.marketbridge.model.AddressValue;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckoutResponse {
    // 배송지 정보
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
