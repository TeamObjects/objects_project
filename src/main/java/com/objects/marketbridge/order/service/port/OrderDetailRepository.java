package com.objects.marketbridge.order.service.port;

import com.objects.marketbridge.common.infra.entity.OrderDetailEntity;
import com.objects.marketbridge.common.infra.entity.ProductEntity;

import java.util.List;

public interface OrderDetailRepository  {

    int changeAllType(Long orderId, String type);

    List<OrderDetailEntity> saveAll(List<OrderDetailEntity> orderDetailEntity);

    void addReason(Long orderId, String reason);

    void deleteAllInBatch();

    void save(OrderDetailEntity orderDetailEntity);

    OrderDetailEntity findById(Long id);

    List<OrderDetailEntity> findByProductId(Long id);

    List<OrderDetailEntity> findAll();

    List<OrderDetailEntity> findByOrderNo(String orderNo);

    List<OrderDetailEntity> findByOrder_IdAndProductIn(Long orderId, List<ProductEntity> products);

    List<OrderDetailEntity> findByOrderNoAndProduct_IdIn(String orderNo, List<Long> productIds);

//    OrderDetail findByStockIdAndOrderId(Long stockId, Long orderId);
}
