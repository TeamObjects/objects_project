package com.objects.marketbridge.common.infra.repository;

import com.objects.marketbridge.common.infra.entity.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryJpaRepository extends JpaRepository<DeliveryEntity, Long> {
}
