package com.objects.marketbridge.domain.order.service;

import com.objects.marketbridge.common.infra.entity.OrderDetailEntity;
import com.objects.marketbridge.order.service.ProductStockService;
import com.objects.marketbridge.order.service.port.OrderDetailRepository;
import com.objects.marketbridge.product.repository.ProductRepository;
import com.objects.marketbridge.common.interceptor.error.CustomLogicException;
import com.objects.marketbridge.common.interceptor.error.ErrorCode;
import com.objects.marketbridge.common.infra.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ProductStockServiceTest {

    @Autowired OrderDetailRepository orderDetailRepository;
    @Autowired ProductRepository productRepository;
    @Autowired
    ProductStockService productStockService;
    @BeforeEach
    void init() {

        Long quantity = 1L;

        List<ProductEntity> products = createProducts();
        productRepository.saveAll(products);

        List<OrderDetailEntity> orderDetailEntities = createOrderDetails(products, quantity);
        orderDetailRepository.saveAll(orderDetailEntities);
    }
    @Test
    @DisplayName("주문이 들어오면 재고가 주문수량만큼 빠져나가야한다")
    void decrease() {

        //given
        List<OrderDetailEntity> orderDetailEntities = orderDetailRepository.findAll();
        List<ProductEntity> products = productRepository.findAll();
        List<Long> stocks = products.stream().map(ProductEntity::getStock).toList();
        List<Long> quantityList = orderDetailEntities.stream().map(OrderDetailEntity::getQuantity).toList();

        //when
        productStockService.decrease(orderDetailEntities);

        //then
        IntStream.range(0, products.size())
                .forEach(i -> assertThat(products.get(i).getStock()).isEqualTo(stocks.get(i)-quantityList.get(i)));
    }

    private List<ProductEntity> createProducts() {

        ProductEntity product1 = ProductEntity.builder().stock(1L).build();
        ProductEntity product2 = ProductEntity.builder().stock(2L).build();
        ProductEntity product3 = ProductEntity.builder().stock(3L).build();

        return List.of(product1, product2, product3);
    }

    private List<OrderDetailEntity> createOrderDetails(List<ProductEntity> products, Long quantity) {

        return products.stream().map(p -> OrderDetailEntity.builder().product(p).quantity(quantity).build()).toList();
    }

    @Test
    @DisplayName("재고가 주문수량보다 적을 경우 예외를 던진다")
    void decrease_error() {

        //given
        List<OrderDetailEntity> orderDetailEntities = orderDetailRepository.findAll();
        List<ProductEntity> products = productRepository.findAll();
        products.get(0).changeStock(0L); // 재고 0 으로 변경
        List<Long> stocks = products.stream().map(ProductEntity::getStock).toList();
        List<Long> quantityList = orderDetailEntities.stream().map(OrderDetailEntity::getQuantity).toList();

        //when, then
        assertThatThrownBy(() ->
                productStockService.decrease(orderDetailEntities))
                .isInstanceOf(CustomLogicException.class)
                .hasMessageContaining(ErrorCode.OUT_OF_STOCK.getMessage());


    }
}