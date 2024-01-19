package com.objects.marketbridge.domain.payment.client;

import com.objects.marketbridge.domain.payment.domain.Payment;
import com.objects.marketbridge.domain.payment.dto.RefundInfoDto;

import java.util.Optional;

public interface RefundClient {

    RefundInfoDto refund(Payment paymentKey, String cancelReason, Long cancelAmount);
}
