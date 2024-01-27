package com.objects.marketbridge.order.service.port;


import com.objects.marketbridge.common.infra.entity.OrderEntity;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Optional<OrderEntity> findById(Long orderId);
    OrderEntity findByOrderNo(String orderNo);

    OrderEntity save(OrderEntity orderEntity);

    void saveAll(List<OrderEntity> orderEntities);
    OrderEntity findWithOrderDetailsAndProduct(Long orderId);

    void deleteAllInBatch();

    OrderEntity findByIdWithOrderDetail(Long orderId);

    Optional<OrderEntity> findOrderWithDetailsAndProduct(Long orderId);

    List<OrderEntity> findDistinctWithDetailsByMemberId(Long memberId);

    OrderEntity findByTid(String tid);

    void deleteByOrderNo(String orderNo);
    // TODO 영속성 문제? 있는 쿼리
//    List<Order> findDistinctWithDetailsByMemberId(Long memberId);


}
