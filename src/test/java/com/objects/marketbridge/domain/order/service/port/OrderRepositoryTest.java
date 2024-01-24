package com.objects.marketbridge.domain.order.service.port;

import com.objects.marketbridge.model.Product;
import com.objects.marketbridge.domain.order.entity.ProdOrder;
import com.objects.marketbridge.domain.order.entity.ProdOrderDetail;
import com.objects.marketbridge.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("주문ID로 주문을 조회한다.")
    public void findByOrderId() {
        // given
        ProdOrder order = ProdOrder.builder().build();
        ProdOrder savedOrder = orderRepository.save(order);

        // when
        Optional<ProdOrder> foundOrder = orderRepository.findById(savedOrder.getId());

        // then
        assertTrue(foundOrder.isPresent());
        assertThat(savedOrder.getId()).isEqualTo(foundOrder.get().getId());
    }

    @Test
    @DisplayName("저장된 주문이 없다면 주문을 조회 할 수 없다.")
    public void findByOrderIdNoSearch() {
        // given
        Long NoId = 1L;

        // when
        Optional<ProdOrder> empty = orderRepository.findById(NoId);

        // then
        assertFalse(empty.isPresent());
        assertThat(empty).isEmpty();
    }

    @Test
    @DisplayName("주문을 저장한다.")
    public void save() {
        // given
        ProdOrder order = ProdOrder.builder().build();

        // when
        ProdOrder savedOrder = orderRepository.save(order);

        // then
        assertNotNull(savedOrder.getId());

        Optional<ProdOrder> foundOrder = orderRepository.findById(savedOrder.getId());
        assertTrue(foundOrder.isPresent());
        assertThat(savedOrder.getId()).isEqualTo(foundOrder.get().getId());
    }

    @Test
    @DisplayName("주문 아이디로 주문, 주문상세, 상품을 한번에 조회 할 수 있다.")
    public void findProdOrderWithDetailsAndProduct() {
        // given
        ProdOrder prodOrder = ProdOrder.builder().build();
        Product product = Product.builder().build();
        ProdOrderDetail prodOrderDetail = ProdOrderDetail.builder()
                .prodOrder(prodOrder)
                .product(product)
                .build();
        prodOrder.addOrderDetail(prodOrderDetail);
        productRepository.save(product);
        orderRepository.save(prodOrder);

        // when
        ProdOrder findOrder = orderRepository.findProdOrderWithDetailsAndProduct(prodOrder.getId()).get();

        // then
        assertThat(findOrder.getId()).isEqualTo(prodOrder.getId());
    }


    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Test
    @DisplayName("")
    public void test() {
        // given
        ProdOrder prodOrder = ProdOrder.builder().build();
        Product product = Product.builder().build();
        ProdOrderDetail prodOrderDetail1 = ProdOrderDetail.builder()
                .prodOrder(prodOrder)
                .price(1000L)
                .product(product)
                .build();
        ProdOrderDetail prodOrderDetail2 = ProdOrderDetail.builder()
                .prodOrder(prodOrder)
                .price(2000L)
                .product(product)
                .build();
        prodOrder.addOrderDetail(prodOrderDetail1);
        prodOrder.addOrderDetail(prodOrderDetail2);

        // when
        orderRepository.save(prodOrder);

        // then
        ProdOrderDetail orderDetail = orderDetailRepository.findById(prodOrderDetail1.getId());
        System.out.println("orderDetail = " + orderDetail);

    }

}