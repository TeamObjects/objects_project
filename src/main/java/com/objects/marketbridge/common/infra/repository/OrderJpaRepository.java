package com.objects.marketbridge.common.infra.repository;

import com.objects.marketbridge.common.infra.entity.OrderEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {

    @EntityGraph(attributePaths = "orderDetails.product")
    @Query("SELECT o FROM OrderEntity o LEFT JOIN FETCH o.orderDetails od LEFT JOIN FETCH od.product WHERE o.id = :orderId")
    Optional<OrderEntity> findWithOrderDetailsAndProduct(@Param("orderId") Long orderId);

    Optional<OrderEntity> findByOrderNo(String orderNo);

    OrderEntity findByTid(String tid);

    void deleteByOrderNo(String orderNo);



}
