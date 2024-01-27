package com.objects.marketbridge.order.infra.repository;

import com.objects.marketbridge.common.infra.repository.OrderJpaRepository;
import com.objects.marketbridge.order.controller.response.OrderCancelReturnListResponse;
import com.objects.marketbridge.order.controller.response.OrderDetailResponse;
import com.objects.marketbridge.domain.order.controller.response.QOrderCancelReturnListResponse;
import com.objects.marketbridge.domain.order.controller.response.QOrderDetailResponse;
import com.objects.marketbridge.order.service.port.OrderDtoRepository;
import com.objects.marketbridge.common.domain.enums.StatusCodeType;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.objects.marketbridge.domain.order.entity.QOrder.order;
import static com.objects.marketbridge.domain.order.entity.QOrderDetail.orderDetail;
import static com.objects.marketbridge.model.QProduct.product;

@Repository
public class OrderDtoRepositoryImpl implements OrderDtoRepository {

    private final OrderJpaRepository orderJpaRepository;

    private final JPAQueryFactory queryFactory;

    public OrderDtoRepositoryImpl(OrderJpaRepository orderJpaRepository, EntityManager em) {
        this.orderJpaRepository = orderJpaRepository;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<OrderCancelReturnListResponse> findOrdersByMemberId(Long memberId, Pageable pageable) {
        List<OrderCancelReturnListResponse> content = getOrderCancelReturnListResponses(memberId);
        Map<String, List<OrderDetailResponse>> orderDetailResponseMap = getOrderDetailResponseMap(findOrderIds(content));
        orderDetailResponseSetting(content, orderDetailResponseMap);

        JPAQuery<OrderCancelReturnListResponse> countQuery = getCountQuery(memberId);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private List<OrderCancelReturnListResponse> getOrderCancelReturnListResponses(Long memberId) {
        List<OrderCancelReturnListResponse> content = queryFactory
                .select(new QOrderCancelReturnListResponse(
                                order.updatedAt,
                                order.createdAt,
                                order.orderNo
                        )
                ).from(order)
                .where(order.member.id.eq(memberId))
                .fetch();
        return content;
    }

    private Map<String, List<OrderDetailResponse>> getOrderDetailResponseMap(List<String> toOrderIds) {
        List<OrderDetailResponse> orderDetailResponseList = queryFactory
                .select(
                        new QOrderDetailResponse(
                                orderDetail.orderNo,
                                orderDetail.product.id,
                                orderDetail.product.productNo,
                                orderDetail.product.name,
                                orderDetail.product.price,
                                orderDetail.quantity,
                                orderDetail.statusCode
                        ))
                .from(orderDetail)
                .join(orderDetail.product, product)
                .where(
                        orderDetail.order.orderNo.in(toOrderIds),
                        orderDetail.statusCode.eq(StatusCodeType.ORDER_CANCEL.getCode())
                                .or(orderDetail.statusCode.eq(StatusCodeType.RETURN_COMPLETED.getCode()))
                ).fetch();


        return orderDetailResponseList.stream()
                .collect(Collectors.groupingBy(OrderDetailResponse::getOrderNo));
    }

    private void orderDetailResponseSetting(List<OrderCancelReturnListResponse> content, Map<String, List<OrderDetailResponse>> orderDetailResponseMap) {
        content.forEach(o-> o.changeOrderDetailResponseList(orderDetailResponseMap.get(o.getOrderNo())));
    }

    private JPAQuery<OrderCancelReturnListResponse> getCountQuery(Long memberId) {
        JPAQuery<OrderCancelReturnListResponse> countQuery = queryFactory
                .select(new QOrderCancelReturnListResponse(
                                order.updatedAt,
                                order.createdAt,
                                order.orderNo
                        )
                ).from(order)
                .where(order.member.id.eq(memberId));
        return countQuery;
    }

    private List<String> findOrderIds(List<OrderCancelReturnListResponse> content) {
        List<String> toOrderIds = content.stream()
                .map(OrderCancelReturnListResponse::getOrderNo)
                .toList();
        return toOrderIds;
    }


}
