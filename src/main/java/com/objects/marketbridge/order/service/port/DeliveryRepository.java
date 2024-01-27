package com.objects.marketbridge.order.service.port;

import com.objects.marketbridge.common.infra.entity.DeliveryEntity;

import java.util.List;

public interface DeliveryRepository {
    DeliveryEntity findById(Long id);

    DeliveryEntity save(DeliveryEntity delivery);

    List<DeliveryEntity> saveAll(List<DeliveryEntity> deliveries);

    List<DeliveryEntity> findAll();

    List<DeliveryEntity> findAllInIds(List<Long> ids);
}
