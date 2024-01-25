package com.objects.marketbridge.domain.order.repository;

import com.objects.marketbridge.domain.order.entity.OrderTemp;
import com.objects.marketbridge.domain.order.entity.ProdOrder;
import com.objects.marketbridge.domain.order.service.port.OrderRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.LockModeType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.objects.marketbridge.model.QProduct.product;
import static com.objects.marketbridge.domain.order.entity.QProdOrder.prodOrder;
import static com.objects.marketbridge.domain.order.entity.QProdOrderDetail.prodOrderDetail;


@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderTempJpaRepository orderTempJpaRepository;

    private final JPAQueryFactory queryFactory;

    public OrderRepositoryImpl(OrderJpaRepository orderJpaRepository, OrderTempJpaRepository orderTempJpaRepository, EntityManager em) {
        this.orderJpaRepository = orderJpaRepository;
        this.orderTempJpaRepository = orderTempJpaRepository;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<ProdOrder> findById(Long orderId) {
        return orderJpaRepository.findById(orderId);
    }

    @Override
    public ProdOrder findByOrderNo(String orderNo) {
        return orderJpaRepository.findByOrderNo(orderNo).orElseThrow(() -> new EntityNotFoundException("엔티티가 존재하지 않습니다"));
    }

    @Override
    public ProdOrder save(ProdOrder order) {
        return orderJpaRepository.save(order);
    }

    @Override
    public Optional<ProdOrder> findWithOrderDetailsAndProduct(Long orderId) {
        return orderJpaRepository.findWithOrderDetailsAndProduct(orderId);
    }

    @Override
    public void deleteAllInBatch() {
        orderJpaRepository.deleteAllInBatch();
    }

    @Override
    public Optional<ProdOrder> findProdOrderWithDetailsAndProduct(Long orderId) {
        return Optional.ofNullable(
                queryFactory
                .selectFrom(prodOrder)
                .join(prodOrder.prodOrderDetails, prodOrderDetail).fetchJoin()
                .join(prodOrderDetail.product, product).fetchJoin()
                .where(prodOrder.id.eq(orderId))
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .fetchOne()
        );
    }


    @Override
    public Optional<ProdOrder> findByIdWithOrderDetail(Long orderId) {
        return null;
    }

    @Override
    public OrderTemp findOrderTempByOrderNo(String orderNo) {
        return orderTempJpaRepository.findOrderTempByOrderNo(orderNo).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void save(OrderTemp orderTemp) {
        orderTempJpaRepository.save(orderTemp);
    }

    @Override
    public void saveAll(List<ProdOrder> orders) {
        orderJpaRepository.saveAll(orders);
    }

    @Override
    public void saveOrderTempAll(List<OrderTemp> orderTempList) {
        orderTempJpaRepository.saveAll(orderTempList);
    }
}
