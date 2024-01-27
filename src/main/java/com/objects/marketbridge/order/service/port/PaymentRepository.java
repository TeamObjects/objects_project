package com.objects.marketbridge.order.service.port;


import com.objects.marketbridge.common.infra.entity.PaymentEntity;

public interface PaymentRepository {
    void save(PaymentEntity paymentEntity);
    PaymentEntity findById(Long id);

    PaymentEntity findByOrderId(Long orderId);

    PaymentEntity findByOrderNo(String orderNo);

    void deleteAllInBatch();
}
