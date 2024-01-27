package com.objects.marketbridge.common.infra.repository;

import com.objects.marketbridge.common.infra.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, Long> {
    PaymentEntity findByOrderId(Long orderId);
    PaymentEntity findByOrderNo(String orderNo);
}
