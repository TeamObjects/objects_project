package com.objects.marketbridge.domain.order.service.port;

import com.objects.marketbridge.common.infra.entity.OrderEntity;
import com.objects.marketbridge.common.infra.entity.OrderDetailEntity;
import com.objects.marketbridge.order.service.port.OrderDetailRepository;
import com.objects.marketbridge.order.service.port.OrderRepository;
import com.objects.marketbridge.product.repository.ProductRepository;
import com.objects.marketbridge.common.infra.entity.ProductEntity;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.objects.marketbridge.common.domain.enums.StatusCodeType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class OrderEntityDetailRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired EntityManager em;


    @Test
    @DisplayName("특정 주문에 해당하는 주문 상세의 상태 코드를 한번에 바꾼다.")
    public void changeAllType() {
        // given
        String givenCodeType = PAYMENT_COMPLETED.getCode();
        String changeCodeType = ORDER_CANCEL.getCode();

        OrderDetailEntity orderDetailEntity1 = createOrderDetail_type(givenCodeType);
        OrderDetailEntity orderDetailEntity2 = createOrderDetail_type(givenCodeType);
        OrderDetailEntity orderDetailEntity3 = createOrderDetail_type(givenCodeType);

        OrderEntity orderEntity = OrderEntity.builder().build();
        orderEntity.addOrderDetail(orderDetailEntity1);
        orderEntity.addOrderDetail(orderDetailEntity2);
        orderEntity.addOrderDetail(orderDetailEntity3);

        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
        Long orderId = savedOrderEntity.getId();

        // when
        int result = orderDetailRepository.changeAllType(orderId, changeCodeType);

        // then
        assertThat(result).isEqualTo(3);
    }

    @Test
    @DisplayName("주문 상세 리스트를 모두 저장한다.")
    public void saveAll() {
        // given
        String givenType = PAYMENT_COMPLETED.getCode();
        OrderDetailEntity orderDetailEntity1 = createOrderDetail_type(givenType);
        OrderDetailEntity orderDetailEntity2 = createOrderDetail_type(givenType);
        OrderDetailEntity orderDetailEntity3 = createOrderDetail_type(givenType);
        List<OrderDetailEntity> orderDetailEntities = List.of(orderDetailEntity1, orderDetailEntity2, orderDetailEntity3);

        // when
        List<OrderDetailEntity> savedOrderDetailEntities = orderDetailRepository.saveAll(orderDetailEntities);

        // then
        assertThat(savedOrderDetailEntities.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("주문 상세에 이유 넣기")
    public void addReason() {
        // given
        String reason = "상품이 맘에들지 않아요";
        OrderDetailEntity orderDetailEntity1 = OrderDetailEntity.builder().build();
        OrderDetailEntity orderDetailEntity2 = OrderDetailEntity.builder().build();
        OrderDetailEntity orderDetailEntity3 = OrderDetailEntity.builder().build();

        OrderEntity orderEntity = OrderEntity.builder().build();
        orderEntity.addOrderDetail(orderDetailEntity1);
        orderEntity.addOrderDetail(orderDetailEntity2);
        orderEntity.addOrderDetail(orderDetailEntity3);

        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
        Long orderId = savedOrderEntity.getId();

        // when
        orderDetailRepository.addReason(orderId, reason);

        // then
        String savedReason = getReason(orderId);
        assertThat(reason).isEqualTo(savedReason);
    }

    @Test
    @DisplayName("주문 ID와 상품 리스트가 주어지면 주문 상세 리스트를 조회할 수 있다.")
    public void findByProdOrder_IdAndProductIn() {
        // given
        ProductEntity product1 = ProductEntity.builder().name("옷").build();
        ProductEntity product2 = ProductEntity.builder().name("바지").build();
        ProductEntity product3 = ProductEntity.builder().name("신발").build();

        OrderDetailEntity orderDetailEntity1 = OrderDetailEntity.builder().product(product1).build();
        OrderDetailEntity orderDetailEntity2 = OrderDetailEntity.builder().product(product2).build();
        OrderDetailEntity orderDetailEntity3 = OrderDetailEntity.builder().product(product3).build();

        OrderEntity orderEntity = OrderEntity.builder().build();
        orderEntity.addOrderDetail(orderDetailEntity1);
        orderEntity.addOrderDetail(orderDetailEntity2);
        orderEntity.addOrderDetail(orderDetailEntity3);

        List<ProductEntity> products = List.of(product1, product2, product3);
        productRepository.saveAll(products);
        orderRepository.save(orderEntity);

        // when
        List<OrderDetailEntity> orderDetailEntities = orderDetailRepository.findByOrder_IdAndProductIn(orderEntity.getId(), products);

        // then
        Assertions.assertThat(orderDetailEntities).hasSize(3)
                .extracting("product", "order")
                .contains(
                        tuple(product1, orderEntity),
                        tuple(product2, orderEntity),
                        tuple(product3, orderEntity)
                );
    }

    @Test
    @DisplayName("주문 번호와 상품 ID 리스트가 주어지면 상품 상세 리스트를 조회할 수 있다.")
    public void findByOrderNoAndProduct_IdIn() {
        // given
        ProductEntity product1 = ProductEntity.builder()
                .price(1000L)
                .thumbImg("썸네일1")
                .name("옷")
                .build();
        ProductEntity product2 = ProductEntity.builder()
                .name("바지")
                .price(2000L)
                .thumbImg("썸네일2")
                .build();
        ProductEntity product3 = ProductEntity.builder()
                .name("신발")
                .price(3000L)
                .thumbImg("썸네일3")
                .build();

        OrderDetailEntity orderDetailEntity1 = OrderDetailEntity.builder()
                .product(product1)
                .quantity(2L)
                .price(product1.getPrice() * 2L)
                .orderNo("123")
                .statusCode(ORDER_CANCEL.getCode())
                .reason("빵빵이")
                .build();
        OrderDetailEntity orderDetailEntity2 = OrderDetailEntity.builder()
                .product(product2)
                .quantity(3L)
                .price(product2.getPrice() * 3L)
                .orderNo("123")
                .statusCode(ORDER_CANCEL.getCode())
                .reason("옥지얌")
                .build();
        OrderDetailEntity orderDetailEntity3 = OrderDetailEntity.builder()
                .product(product3)
                .quantity(4L)
                .price(product3.getPrice() * 4L)
                .orderNo("123")
                .statusCode(RETURN_COMPLETED.getCode())
                .reason("멍청이")
                .build();

        OrderEntity orderEntity = OrderEntity.builder()
                .orderNo("123")
                .build();
        orderEntity.addOrderDetail(orderDetailEntity1);
        orderEntity.addOrderDetail(orderDetailEntity2);
        orderEntity.addOrderDetail(orderDetailEntity3);

        List<ProductEntity> products = List.of(product1, product2, product3);
        productRepository.saveAll(products);
        orderRepository.save(orderEntity);
        List<Long> productIds = products.stream().map(ProductEntity::getId).toList();

        // when
        List<OrderDetailEntity> orderDetailEntities = orderDetailRepository.findByOrderNoAndProduct_IdIn(orderEntity.getOrderNo(), productIds);

        // then
        assertThat(orderDetailEntities).hasSize(3)
                .extracting("orderNo", "product", "quantity", "price", "statusCode", "reason")
                .contains(
                        tuple("123", product1, 2L, 2000L, ORDER_CANCEL.getCode(), "빵빵이"),
                        tuple("123", product2, 3L, 6000L, ORDER_CANCEL.getCode(), "옥지얌"),
                        tuple("123", product3, 4L, 12000L, RETURN_COMPLETED.getCode(), "멍청이")
                );
    }


    private String getReason(Long orderId) {
        List<OrderDetailEntity> orderDetailEntities = orderRepository.findById(orderId).get().getOrderDetailEntities();
        return orderDetailEntities.get(0).getReason();
    }

    private OrderDetailEntity createOrderDetail_type(String code) {
        return OrderDetailEntity.builder()
                .statusCode(code)
                .build();
    }


}