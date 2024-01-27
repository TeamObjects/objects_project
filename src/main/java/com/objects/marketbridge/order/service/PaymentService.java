package com.objects.marketbridge.order.service;

import com.objects.marketbridge.common.infra.entity.OrderEntity;
import com.objects.marketbridge.common.infra.entity.OrderDetailEntity;
import com.objects.marketbridge.common.domain.enums.StatusCodeType;
import com.objects.marketbridge.common.infra.entity.PaymentEntity;
import com.objects.marketbridge.order.payment.controller.response.KakaoPayApproveResponse;
import com.objects.marketbridge.order.service.port.OrderRepository;
import com.objects.marketbridge.order.domain.Amount;
import com.objects.marketbridge.order.domain.CardInfo;
import com.objects.marketbridge.order.service.port.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public void create(KakaoPayApproveResponse response) {

        // 1. Payment 엔티티 생성
        PaymentEntity paymentEntity = createPayment(response);

        // 2. Order - Payment 연관관계 매핑
        OrderEntity orderEntity = orderRepository.findByOrderNo(response.getPartnerOrderId());
        paymentEntity.linkOrder(orderEntity);

        // 3. orderDetail 의 statusCode 업데이트
        List<OrderDetailEntity> orderDetailEntities = orderEntity.getOrderDetailEntities();
        orderDetailEntities.forEach(o -> o.changeStatusCode(StatusCodeType.PAYMENT_COMPLETED.getCode()));

        // 4. 영속성 저장
        paymentRepository.save(paymentEntity);

        //TODO : 5. 판매자 계좌 변경
    }

    private PaymentEntity createPayment(KakaoPayApproveResponse response) {

        String orderNo = response.getPartnerOrderId();
        String paymentMethod = response.getPaymentMethodType();
        String tid = response.getTid();
        CardInfo cardInfo = response.getCardInfo();
        Amount amount = response.getAmount();

        return PaymentEntity.create(orderNo, paymentMethod, tid, cardInfo, amount);
    }
}
