package com.objects.marketbridge.order.infra.repository;

import com.objects.marketbridge.common.infra.repository.OrderDetailJpaRepository;
import com.objects.marketbridge.common.infra.entity.OrderDetailEntity;
import com.objects.marketbridge.order.service.port.OrderDetailRepository;
import com.objects.marketbridge.common.infra.entity.ProductEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class OrderDetailRepositoryImpl implements OrderDetailRepository {

    private final OrderDetailJpaRepository orderDetailJpaRepository;
    private final JPAQueryFactory queryFactory;

    public OrderDetailRepositoryImpl(OrderDetailJpaRepository orderDetailJpaRepository, EntityManager em) {
        this.orderDetailJpaRepository = orderDetailJpaRepository;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public int changeAllType(Long orderId, String type) {
        return orderDetailJpaRepository.changeAllType(orderId, type);
    }

    @Override
    public List<OrderDetailEntity> saveAll(List<OrderDetailEntity> orderDetailEntity) {
        return orderDetailJpaRepository.saveAll(orderDetailEntity);
    }

    @Override
    public void addReason(Long orderId, String reason) {
        orderDetailJpaRepository.addReason(orderId, reason);
    }

    @Override
    public void save(OrderDetailEntity orderDetailEntity) {
        orderDetailJpaRepository.save(orderDetailEntity);
    }

    @Override
    public void deleteAllInBatch() {
        orderDetailJpaRepository.deleteAllInBatch();
    }

    @Override
    public OrderDetailEntity findById(Long id) {
        return orderDetailJpaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("엔티티가 존재하지 않습니다"));
    }

    @Override
    public List<OrderDetailEntity> findByProductId(Long id) {
        return orderDetailJpaRepository.findByProductId(id);
    }

    @Override
    public List<OrderDetailEntity> findAll() {
        return orderDetailJpaRepository.findAll();
    }

    @Override
    public List<OrderDetailEntity> findByOrderNo(String orderNo) {
        return orderDetailJpaRepository.findByOrderNo(orderNo);
    }

    @Override
    public List<OrderDetailEntity> findByOrder_IdAndProductIn(Long orderId, List<ProductEntity> products) {
        return orderDetailJpaRepository.findByOrder_IdAndProductIn(orderId, products);
    }

    @Override
    public List<OrderDetailEntity> findByOrderNoAndProduct_IdIn(String orderNo, List<Long> productIds) {
        return orderDetailJpaRepository.findByOrderNoAndProduct_IdIn(orderNo, productIds);
    }

}
