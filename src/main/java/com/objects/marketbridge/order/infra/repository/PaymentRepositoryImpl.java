package com.objects.marketbridge.order.infra.repository;

import com.objects.marketbridge.common.infra.entity.PaymentEntity;
import com.objects.marketbridge.common.infra.repository.PaymentJpaRepository;
import com.objects.marketbridge.order.service.port.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {
    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public void save(PaymentEntity paymentEntity) {
        paymentJpaRepository.save(paymentEntity);
    }

    @Override
    public PaymentEntity findById(Long id) {
        return paymentJpaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("엔티티가 존재하지 않습니다."));
    }

    @Override
    public PaymentEntity findByOrderId(Long orderId) {
        return paymentJpaRepository.findByOrderId(orderId);
    }

    @Override
    public PaymentEntity findByOrderNo(String orderNo) {
        return paymentJpaRepository.findByOrderNo(orderNo);
    }

    @Override
    public void deleteAllInBatch() {
        paymentJpaRepository.deleteAllInBatch();
    }
}
