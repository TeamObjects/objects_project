package com.objects.marketbridge.domain.order.repository;

import com.objects.marketbridge.domain.order.entity.ProdOrder;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
public interface OrderJpaRepository extends JpaRepository<ProdOrder, Long> {

    @EntityGraph(attributePaths = "prodOrderDetails.product")
    @Query("SELECT o FROM ProdOrder o LEFT JOIN FETCH o.prodOrderDetails od LEFT JOIN FETCH od.product WHERE o.id = :prodOrderId")
    Optional<ProdOrder> findWithOrderDetailsAndProduct(@Param("prodOrderId") Long prodOrderId);

    Optional<ProdOrder> findByOrderNo(String orderNo);

}
