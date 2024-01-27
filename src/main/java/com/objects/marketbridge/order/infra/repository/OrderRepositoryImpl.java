package com.objects.marketbridge.order.infra.repository;

import com.objects.marketbridge.common.infra.entity.OrderEntity;
import com.objects.marketbridge.common.infra.repository.OrderJpaRepository;
import com.objects.marketbridge.order.service.port.OrderRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.LockModeType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.objects.marketbridge.domain.order.entity.QOrder.order;
import static com.objects.marketbridge.domain.order.entity.QOrderDetail.orderDetail;
import static com.objects.marketbridge.model.QProduct.product;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final JPAQueryFactory queryFactory;

    public OrderRepositoryImpl(OrderJpaRepository orderJpaRepository, EntityManager em) {
        this.orderJpaRepository = orderJpaRepository;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<OrderEntity> findById(Long orderId) {
        return orderJpaRepository.findById(orderId);
    }

    @Override
    public OrderEntity findByOrderNo(String orderNo) {
        return orderJpaRepository.findByOrderNo(orderNo).orElseThrow(() -> new EntityNotFoundException("엔티티가 존재하지 않습니다"));
    }

    @Override
    public OrderEntity save(OrderEntity orderEntity) {
        return orderJpaRepository.save(orderEntity);
    }

    @Override
    public OrderEntity findWithOrderDetailsAndProduct(Long orderId) {
        return orderJpaRepository.findWithOrderDetailsAndProduct(orderId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void deleteAllInBatch() {
        orderJpaRepository.deleteAllInBatch();
    }

    @Override
    public Optional<OrderEntity> findOrderWithDetailsAndProduct(Long orderId) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(order)
                        .join(order.orderDetails, orderDetail).fetchJoin()
                        .join(orderDetail.product, product).fetchJoin()
                        .where(order.id.eq(orderId))
                        .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                        .fetchOne()
        );
    }

    @Override
    public OrderEntity findByTid(String tid) {
        return orderJpaRepository.findByTid(tid);
    }

    @Override
    public List<OrderEntity> findDistinctWithDetailsByMemberId(Long memberId) {
        return null;
    }

    @Override
    public OrderEntity findByIdWithOrderDetail(Long orderId) {
        return null;
    }


    @Override
    public void saveAll(List<OrderEntity> orderEntities) {
        orderJpaRepository.saveAll(orderEntities);
    }

    @Override
    public void deleteByOrderNo(String orderNo) {
        orderJpaRepository.deleteByOrderNo(orderNo);
    }



    //    @Override
//    public List<Order> findDistinctWithDetailsByMemberId(Long memberId) {
//
//        BooleanExpression statusCondition = orderDetail.statusCode.eq(ORDER_CANCEL.getCode());
//        BooleanExpression orCondition = statusCondition.or(orderDetail.statusCode.eq(RETURN_COMPLETED.getCode()));
//
//        return queryFactory
//                .selectDistinct(order)
//                .from(order)
//                .join(order.orderDetails, orderDetail).fetchJoin()
//                .where(
//                        order.member.id.eq(memberId),
//                        orCondition
//                )
//                .fetch();
//    }

}
