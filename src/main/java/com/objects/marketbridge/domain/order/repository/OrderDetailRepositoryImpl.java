package com.objects.marketbridge.domain.order.repository;

import com.objects.marketbridge.domain.order.entity.ProdOrderDetail;
import com.objects.marketbridge.domain.order.service.port.OrderDetailRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.objects.marketbridge.domain.model.QProdOption.prodOption;
import static com.objects.marketbridge.domain.order.entity.QProdOrder.*;
import static com.objects.marketbridge.domain.order.entity.QProdOrderDetail.prodOrderDetail;

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
    public List<ProdOrderDetail> saveAll(List<ProdOrderDetail> orderDetail) {
        return orderDetailJpaRepository.saveAll(orderDetail);
    }

    @Override
    public void addReason(Long orderId, String reason) {
        orderDetailJpaRepository.addReason(orderId, reason);
    }

    @Override
    public void save(ProdOrderDetail prodOrderDetail) {
        orderDetailJpaRepository.save(prodOrderDetail);
    }

    @Override
    public void deleteAllInBatch() {
        orderDetailJpaRepository.deleteAllInBatch();
    }

    @Override
    public ProdOrderDetail findById(Long id) {
        return orderDetailJpaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("엔티티가 존재하지 않습니다"));
    }

    @Override
    public List<ProdOrderDetail> findByProductId(Long id) {
        return orderDetailJpaRepository.findByProductId(id);
    }

    @Override
    public List<ProdOrderDetail> findAll() {
        return orderDetailJpaRepository.findAll();
    }

    @Override
    public List<ProdOrderDetail> findByOrderNo(String orderNo) {
        return orderDetailJpaRepository.findByOrderNo(orderNo);
    }

    //    @Override
//    public ProdOrderDetail findByStockIdAndOrderId(Long stockId, Long orderId) {
//        return queryFactory.selectFrom(prodOrderDetail)
//                .join(prodOrderDetail.prodOption, prodOption)
//                .join(prodOption.stocks, stock)
//                .where(
//                        prodOrder.id.eq(orderId),
//                        stock.id.eq(stockId)
//                )
//                .fetchOne();
//    }
}
