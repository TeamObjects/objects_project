//package com.objects.marketbridge.domain.payment.service;
//
//import com.objects.marketbridge.domain.payment.client.RefundClient;
//import com.objects.marketbridge.domain.payment.domain.Payment;
//import com.objects.marketbridge.domain.payment.dto.RefundDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class RefundService {
//
//    private final RefundClient refundClient;
//
//    public RefundDto refund(Payment paymentKey, String cancelReason, Long cancelAmount) {
//        return RefundDto.of(refundClient.refund(paymentKey, cancelReason, cancelAmount));
//    }
//}
