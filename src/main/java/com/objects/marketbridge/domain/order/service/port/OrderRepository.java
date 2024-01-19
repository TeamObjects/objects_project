package com.objects.marketbridge.domain.order.service.port;


import com.objects.marketbridge.domain.order.entity.OrderTemp;
import com.objects.marketbridge.domain.order.entity.ProdOrder;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Optional<ProdOrder> findById(Long orderId);
    ProdOrder findByOrderNo(String orderNo);

    ProdOrder save(ProdOrder order);

    void saveAll(List<ProdOrder> orders);
    Optional<ProdOrder> findWithOrderDetailsAndProduct(Long orderId);

    void deleteAllInBatch();

    Optional<ProdOrder> findByIdWithOrderDetail(Long orderId);

    OrderTemp findOrderTempByOrderNo(String orderNo);

    void save(OrderTemp orderTemp);

    void saveOrderTempAll(List<OrderTemp> orderTempList);

    Optional<ProdOrder> findProdOrderWithDetailsAndProduct(Long orderId);
}
