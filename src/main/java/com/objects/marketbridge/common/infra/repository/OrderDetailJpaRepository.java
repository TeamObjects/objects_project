package com.objects.marketbridge.common.infra.repository;

import com.objects.marketbridge.common.infra.entity.OrderDetailEntity;
import com.objects.marketbridge.common.infra.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailJpaRepository extends JpaRepository<OrderDetailEntity, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update OrderDetailEntity pod set pod.statusCode = :type where pod.order.id = :orderId")
    int changeAllType(@Param("orderId") Long orderId, @Param("type") String type);

    @Modifying(clearAutomatically = true)
    @Query("update OrderDetailEntity pod set pod.reason = :reason where pod.order.id = :orderId")
    void addReason(@Param("orderId") Long orderId, @Param("reason") String reason);

    List<OrderDetailEntity> findByProductId(Long memberId);

    List<OrderDetailEntity> findByOrderNo(String orderNo);

    List<OrderDetailEntity> findByOrder_IdAndProductIn(Long orderId, List<ProductEntity> products);

    List<OrderDetailEntity> findByOrderNoAndProduct_IdIn(String orderNo, List<Long> productIds);

}
