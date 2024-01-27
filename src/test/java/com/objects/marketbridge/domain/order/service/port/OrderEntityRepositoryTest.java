package com.objects.marketbridge.domain.order.service.port;

import com.objects.marketbridge.common.infra.entity.OrderEntity;
import com.objects.marketbridge.common.infra.entity.ProductEntity;
import com.objects.marketbridge.common.infra.entity.OrderDetailEntity;
import com.objects.marketbridge.member.service.port.MemberRepository;
import com.objects.marketbridge.order.service.port.OrderRepository;
import com.objects.marketbridge.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class OrderEntityRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("주문ID로 주문을 조회한다.")
    public void findByOrderId() {
        // given
        OrderEntity orderEntity = OrderEntity.builder().build();
        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);

        // when
        Optional<OrderEntity> foundOrder = orderRepository.findById(savedOrderEntity.getId());

        // then
        assertTrue(foundOrder.isPresent());
        assertThat(savedOrderEntity.getId()).isEqualTo(foundOrder.get().getId());
    }

    @Test
    @DisplayName("저장된 주문이 없다면 주문을 조회 할 수 없다.")
    public void findByOrderIdNoSearch() {
        // given
        Long NoId = 1L;

        // when
        Optional<OrderEntity> empty = orderRepository.findById(NoId);

        // then
        assertFalse(empty.isPresent());
        assertThat(empty).isEmpty();
    }

    @Test
    @DisplayName("주문을 저장한다.")
    public void save() {
        // given
        OrderEntity orderEntity = OrderEntity.builder().build();

        // when
        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);

        // then
        assertNotNull(savedOrderEntity.getId());

        Optional<OrderEntity> foundOrder = orderRepository.findById(savedOrderEntity.getId());
        assertTrue(foundOrder.isPresent());
        assertThat(savedOrderEntity.getId()).isEqualTo(foundOrder.get().getId());
    }

    @Test
    @DisplayName("주문 아이디로 주문, 주문상세, 상품을 한번에 조회 할 수 있다.")
    @Rollback(value = false)
    public void findOrderWithDetailsAndProduct() {
        // given
        OrderEntity orderEntity = OrderEntity.builder().build();
        ProductEntity product = ProductEntity.builder().build();
        OrderDetailEntity orderDetailEntity = OrderDetailEntity.builder()
                .order(orderEntity)
                .product(product)
                .build();
        orderEntity.addOrderDetail(orderDetailEntity);
        productRepository.save(product);
        orderRepository.save(orderEntity);

        // when
        OrderEntity findOrderEntity = orderRepository.findOrderWithDetailsAndProduct(orderEntity.getId()).get();

        // then
        assertThat(findOrderEntity.getId()).isEqualTo(orderEntity.getId());
    }

}