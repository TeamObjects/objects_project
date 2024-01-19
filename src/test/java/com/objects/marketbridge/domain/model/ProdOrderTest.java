package com.objects.marketbridge.domain.model;

import com.objects.marketbridge.domain.order.entity.ProdOrder;
import com.objects.marketbridge.domain.order.entity.ProdOrderDetail;
import com.objects.marketbridge.domain.order.entity.QProdOrderDetail;
import com.objects.marketbridge.domain.order.service.port.OrderDetailRepository;
import com.objects.marketbridge.domain.order.service.port.OrderRepository;
import com.objects.marketbridge.domain.product.repository.ProductRepository;
import jakarta.persistence.Access;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.AccessType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class ProdOrderTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private EntityManager em;
    @Test
    @DisplayName("주문 취소시 사용한 유저 쿠폰이 모두 반환되야 한다.")
    public void returnCoupon() {
        // given
        LocalDateTime useDate = LocalDateTime.of(2024, 1, 16, 7, 14);

        ProdOrder prodOrder = ProdOrder.builder()
                .build();

        Product product1 = Product.builder()
                .build();
        Product product2 = Product.builder()
                .build();

        Coupon coupon1 = Coupon.builder()
                .product(product1)
                .price(1000L)
                .count(10L)
                .build();
        Coupon coupon2 = Coupon.builder()
                .product(product2)
                .price(2000L)
                .count(20L)
                .build();

        ProdOrderDetail prodOrderDetail1 = ProdOrderDetail.builder()
                .prodOrder(prodOrder)
                .coupon(coupon1)
                .product(product1)
                .build();
        ProdOrderDetail prodOrderDetail2 = ProdOrderDetail.builder()
                .prodOrder(prodOrder)
                .coupon(coupon2)
                .product(product2)
                .build();

        MemberCoupon memberCoupon1 = MemberCoupon.builder()
                .coupon(coupon1)
                .isUsed(true)
                .usedDate(useDate)
                .build();
        MemberCoupon memberCoupon2 = MemberCoupon.builder()
                .coupon(coupon2)
                .isUsed(true)
                .usedDate(useDate)
                .build();

        orderRepository.save(prodOrder);
        orderDetailRepository.saveAll(List.of(prodOrderDetail1, prodOrderDetail2));
        productRepository.saveAll(List.of(product1, product2));
        prodOrder.addOrderDetail(prodOrderDetail1);
        prodOrder.addOrderDetail(prodOrderDetail2);
        em.persist(coupon1);
        em.persist(coupon2);
        em.persist(memberCoupon1);
        em.persist(memberCoupon2);
        coupon1.addMemberCoupon(memberCoupon1);
        coupon2.addMemberCoupon(memberCoupon2);

        ProdOrder findOrder = orderRepository.findById(prodOrder.getId()).get();

        // when
        findOrder.returnCoupon();

        // then
        assertThat(coupon1.getCount()).isEqualTo(11L);
        assertThat(coupon2.getCount()).isEqualTo(21L);
        assertThat(memberCoupon1.getUsedDate()).isNull();
        assertThat(memberCoupon2.getUsedDate()).isNull();
        assertThat(memberCoupon1.isUsed()).isFalse();
        assertThat(memberCoupon2.isUsed()).isFalse();
    }

}