package com.objects.marketbridge.domain.payment.service;

import com.objects.marketbridge.domain.order.controller.response.RefundInfo;
import com.objects.marketbridge.domain.payment.client.RefundClient;
import com.objects.marketbridge.domain.payment.domain.Payment;
import com.objects.marketbridge.domain.payment.domain.RefundHistory;
import com.objects.marketbridge.domain.payment.dto.RefundDto;
import com.objects.marketbridge.domain.payment.dto.RefundInfoDto;
import com.objects.marketbridge.domain.payment.repository.RefundHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefundService {

    private final RefundClient refundClient;

    public RefundDto refund(Payment paymentKey, String cancelReason, Long cancelAmount) {
        return RefundDto.of(refundClient.refund(paymentKey, cancelReason, cancelAmount));
    }
}
