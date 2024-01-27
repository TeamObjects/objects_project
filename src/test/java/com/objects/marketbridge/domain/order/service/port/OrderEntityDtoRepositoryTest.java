package com.objects.marketbridge.domain.order.service.port;

import com.objects.marketbridge.common.infra.entity.OrderEntity;
import com.objects.marketbridge.member.service.port.MemberRepository;
import com.objects.marketbridge.order.controller.response.OrderCancelReturnListResponse;
import com.objects.marketbridge.order.controller.response.OrderDetailResponse;
import com.objects.marketbridge.common.infra.entity.OrderDetailEntity;
import com.objects.marketbridge.order.service.port.OrderDtoRepository;
import com.objects.marketbridge.order.service.port.OrderRepository;
import com.objects.marketbridge.product.repository.ProductRepository;
import com.objects.marketbridge.common.infra.entity.MemberEntity;
import com.objects.marketbridge.common.infra.entity.ProductEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.objects.marketbridge.common.domain.enums.StatusCodeType.*;
import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class OrderEntityDtoRepositoryTest {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    OrderDtoRepository orderDtoRepository;
    @Autowired
    EntityManager entityManager;

    @BeforeEach
    public void beforeEach() {

    }

    @Test
    @DisplayName("유저가 반품, 취소한 상품들을 조회할 수 있다.")
    public void findOrdersByMemberId() {
        // given
        MemberEntity member = MemberEntity.builder().build();

        OrderEntity orderEntity1 = OrderEntity.builder()
                .member(member)
                .orderNo("123")
                .build();

        OrderEntity orderEntity2 = OrderEntity.builder()
                .member(member)
                .orderNo("456")
                .build();

        ProductEntity product1 = ProductEntity.builder()
                .productNo("1")
                .name("옷")
                .price(1000L)
                .build();
        ProductEntity product2 = ProductEntity.builder()
                .productNo("2")
                .name("신발")
                .price(2000L)
                .build();
        ProductEntity product3 = ProductEntity.builder()
                .productNo("3")
                .name("바지")
                .price(3000L)
                .build();

        OrderDetailEntity orderDetailEntity1 = OrderDetailEntity.builder()
                .order(orderEntity1)
                .product(product1)
                .quantity(1L)
                .orderNo(orderEntity1.getOrderNo())
                .statusCode(RETURN_COMPLETED.getCode())
                .build();
        OrderDetailEntity orderDetailEntity2 = OrderDetailEntity.builder()
                .order(orderEntity1)
                .product(product2)
                .quantity(2L)
                .orderNo(orderEntity1.getOrderNo())
                .statusCode(ORDER_CANCEL.getCode())
                .build();
        OrderDetailEntity orderDetailEntity3 = OrderDetailEntity.builder()
                .order(orderEntity2)
                .product(product3)
                .quantity(3L)
                .orderNo(orderEntity2.getOrderNo())
                .statusCode(ORDER_CANCEL.getCode())
                .build();
        OrderDetailEntity orderDetailEntity4 = OrderDetailEntity.builder()
                .order(orderEntity2)
                .product(product2)
                .quantity(4L)
                .orderNo(orderEntity2.getOrderNo())
                .statusCode(DELIVERY_ING.getCode())
                .build();

        orderEntity1.addOrderDetail(orderDetailEntity1);
        orderEntity1.addOrderDetail(orderDetailEntity2);
        orderEntity2.addOrderDetail(orderDetailEntity3);

        productRepository.saveAll(List.of(product1, product2, product3));
        memberRepository.save(member);
        orderRepository.save(orderEntity1);
        orderRepository.save(orderEntity2);

        // when
        Page<OrderCancelReturnListResponse> orderCancelReturnListResponsePage = orderDtoRepository.findOrdersByMemberId(member.getId(), PageRequest.of(0, 3));
        List<OrderCancelReturnListResponse> content = orderCancelReturnListResponsePage.getContent();
        // then
        assertThat(content).hasSize(2)
                .extracting("orderNo")
                .contains("123", "456");

        List<OrderDetailResponse> orderDetailResponses1 = content.get(0).getOrderDetailResponses();
        List<OrderDetailResponse> orderDetailResponses2 = content.get(1).getOrderDetailResponses();

        assertThat(orderDetailResponses1).hasSize(2)
                .extracting("orderNo", "productId", "productNo", "name", "price", "quantity", "orderStatus")
                .contains(
                        tuple("123", 1L, "1", "옷", 1000L, 1L, RETURN_COMPLETED.getCode()),
                        tuple("123", 2L, "2", "신발", 2000L, 2L, ORDER_CANCEL.getCode())
                );

        assertThat(orderDetailResponses2).hasSize(1)
                .extracting("orderNo", "productId", "productNo", "name", "price", "quantity", "orderStatus")
                .contains(
                        tuple("456", 3L, "3", "바지", 3000L, 3L, ORDER_CANCEL.getCode())
                );

    }

}