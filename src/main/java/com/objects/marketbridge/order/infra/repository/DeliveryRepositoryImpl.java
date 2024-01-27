package com.objects.marketbridge.order.infra.repository;

import com.objects.marketbridge.common.infra.repository.DeliveryJpaRepository;
import com.objects.marketbridge.common.infra.entity.DeliveryEntity;
import com.objects.marketbridge.order.service.port.DeliveryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DeliveryRepositoryImpl implements DeliveryRepository {

    private final DeliveryJpaRepository deliveryJpaRepository;

    @Override
    public DeliveryEntity findById(Long id) {
        return deliveryJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public DeliveryEntity save(DeliveryEntity delivery) {
        return deliveryJpaRepository.save(delivery);
    }

    @Override
    public List<DeliveryEntity> saveAll(List<DeliveryEntity> deliveries) {
        return deliveryJpaRepository.saveAll(deliveries);
    }

    @Override
    public List<DeliveryEntity> findAll() {
        return deliveryJpaRepository.findAll();
    }

    @Override
    public List<DeliveryEntity> findAllInIds(List<Long> ids) {
        return deliveryJpaRepository.findAllById(ids);
    }
}
