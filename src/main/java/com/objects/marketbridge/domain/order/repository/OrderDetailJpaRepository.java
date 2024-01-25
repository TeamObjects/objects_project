package com.objects.marketbridge.domain.order.repository;

import com.objects.marketbridge.domain.order.entity.ProdOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailJpaRepository extends JpaRepository<ProdOrderDetail, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update ProdOrderDetail pod set pod.statusCode = :type where pod.prodOrder.id = :prodOrderId")
    int changeAllType(@Param("prodOrderId") Long prodOrderId, @Param("type") String type);

    @Modifying(clearAutomatically = true)
    @Query("update ProdOrderDetail pod set pod.reason = :reason where pod.prodOrder.id = :prodOrderId")
    void addReason(@Param("prodOrderId") Long prodOrderId, @Param("reason") String reason);

    List<ProdOrderDetail> findByProductId(Long memberId);

    List<ProdOrderDetail> findByOrderNo(String orderNo);


}
