package com.objects.marketbridge.domain.order.repository;

import com.objects.marketbridge.domain.order.entity.OrderTemp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderTempJpaRepository extends JpaRepository<OrderTemp, Long> {
    Optional<OrderTemp> findOrderTempByOrderNo(String orderNo);

}
