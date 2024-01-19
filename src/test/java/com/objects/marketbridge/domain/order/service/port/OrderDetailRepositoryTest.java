package com.objects.marketbridge.domain.order.service.port;

import com.objects.marketbridge.domain.model.*;
import com.objects.marketbridge.domain.order.entity.ProdOrder;
import com.objects.marketbridge.domain.order.entity.ProdOrderDetail;
import com.objects.marketbridge.domain.order.entity.StatusCodeType;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class OrderDetailRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired EntityManager em;


    @Test
    @DisplayName("특정 주문에 해당하는 주문 상세의 상태 코드를 한번에 바꾼다.")
    public void changeAllType() {
        // given
        String givenCodeType = StatusCodeType.PAYMENT_COMPLETED.getCode();
        String changeCodeType = StatusCodeType.ORDER_CANCEL.getCode();

        ProdOrderDetail orderDetail1 = createOrderDetail_type(givenCodeType);
        ProdOrderDetail orderDetail2 = createOrderDetail_type(givenCodeType);
        ProdOrderDetail orderDetail3 = createOrderDetail_type(givenCodeType);

        ProdOrder order = ProdOrder.builder().build();
        order.addOrderDetail(orderDetail1);
        order.addOrderDetail(orderDetail2);
        order.addOrderDetail(orderDetail3);

        ProdOrder savedOrder = orderRepository.save(order);
        Long orderId = savedOrder.getId();

        // when
        int result = orderDetailRepository.changeAllType(orderId, changeCodeType);

        // then
        assertThat(result).isEqualTo(3);
    }

    @Test
    @DisplayName("주문 상세 리스트를 모두 저장한다.")
    public void saveAll() {
        // given
        String givenType = StatusCodeType.PAYMENT_COMPLETED.getCode();
        ProdOrderDetail orderDetail1 = createOrderDetail_type(givenType);
        ProdOrderDetail orderDetail2 = createOrderDetail_type(givenType);
        ProdOrderDetail orderDetail3 = createOrderDetail_type(givenType);
        List<ProdOrderDetail> orderDetails = List.of(orderDetail1, orderDetail2, orderDetail3);

        // when
        List<ProdOrderDetail> savedOrderDetails = orderDetailRepository.saveAll(orderDetails);

        // then
        assertThat(savedOrderDetails.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("주문 상세에 이유 넣기")
    public void addReason() {
        // given
        String reason = "상품이 맘에들지 않아요";
        ProdOrderDetail orderDetail1 = ProdOrderDetail.builder().build();
        ProdOrderDetail orderDetail2 = ProdOrderDetail.builder().build();
        ProdOrderDetail orderDetail3 = ProdOrderDetail.builder().build();

        ProdOrder order = ProdOrder.builder().build();
        order.addOrderDetail(orderDetail1);
        order.addOrderDetail(orderDetail2);
        order.addOrderDetail(orderDetail3);

        ProdOrder savedOrder = orderRepository.save(order);
        Long orderId = savedOrder.getId();

        // when
        orderDetailRepository.addReason(orderId, reason);

        // then
        String savedReason = getReason(orderId);
        assertThat(reason).isEqualTo(savedReason);
    }
    
//    @Test
//    @DisplayName("")
//    @Rollback(value = false)
//    public void findByStockIdAndOrderId() {
//        Product product1 = Product.builder()
//                .price(10000L)
//                .name("옷")
//                .build();
//        Product product2= Product.builder()
//                .price(20000L)
//                .name("신발")
//                .build();
//        em.persist(product1);
//        em.persist(product2);
//
//        OptionCategory optionCategory = OptionCategory.builder()
//                .name("사이즈")
//                .build();
//        em.persist(optionCategory);
//
//        Option option1 = Option.builder()
//                .optionCategory(optionCategory)
//                .name("M")
//                .build();
//        Option option2 = Option.builder()
//                .optionCategory(optionCategory)
//                .name("L")
//                .build();
//        Option option3 = Option.builder()
//                .optionCategory(optionCategory)
//                .name("250")
//                .build();
//        Option option4 = Option.builder()
//                .optionCategory(optionCategory)
//                .name("260")
//                .build();
//        em.persist(option1);
//        em.persist(option2);
//        em.persist(option3);
//        em.persist(option4);
//
//        ProdOption prodOption1 = ProdOption.builder()
//                .option(option1)
//                .product(product1)
//                .build();
//        ProdOption prodOption2 = ProdOption.builder()
//                .option(option2)
//                .product(product1)
//                .build();
//        ProdOption prodOption3 = ProdOption.builder()
//                .option(option3)
//                .product(product2)
//                .build();
//        ProdOption prodOption4 = ProdOption.builder()
//                .option(option4)
//                .product(product2)
//                .build();
//        em.persist(prodOption1);
//        em.persist(prodOption2);
//        em.persist(prodOption3);
//        em.persist(prodOption4);
//
//        AddressValue addressValue1 = AddressValue.builder()
//                .name("인천 창고 주소")
//                .build();
//        AddressValue addressValue2 = AddressValue.builder()
//                .name("대구 창고 주소")
//                .build();
//
//        Seller seller1 = Seller.builder()
//                .name("판매자1")
//                .build();
//        Seller seller2 = Seller.builder()
//                .name("판매자2")
//                .build();
//        em.persist(seller1);
//        em.persist(seller2);
//
//        Member member = Member.builder()
//                .name("화나게하지마")
//                .build();
//        em.persist(member);
//
//        Coupon coupon1 = Coupon.builder()
//                .price(1000L)
//                .product(product1)
//                .name("옷 할인 쿠폰")
//                .build();
//        Coupon coupon2 = Coupon.builder()
//                .price(2000L)
//                .product(product2)
//                .name("신발 할인 쿠폰")
//                .build();
//        em.persist(coupon1);
//        em.persist(coupon2);
//
//        MemberCoupon memberCoupon1 = MemberCoupon.builder()
//                .member(member)
//                .coupon(coupon1)
//                .isUsed(true)
//                .build();
//        MemberCoupon memberCoupon2 = MemberCoupon.builder()
//                .member(member)
//                .coupon(coupon2)
//                .isUsed(true)
//                .build();
//        em.persist(memberCoupon1);
//        em.persist(memberCoupon2);
//
//        Point point = Point.builder()
//                .outPoint(0L)
//                .balance(10000L)
//                .member(member)
//                .build();
//        em.persist(point);
//
//        // given
//        ProdOrder prodOrder = ProdOrder.builder()
//                .member(member)
//                .build();
//
//        // 상품 옵션 추가
//        ProdOrderDetail prodOrderDetail1 = ProdOrderDetail.builder()
//                .prodOrder(prodOrder)
//                .product(prodOption1)
//                .quantity(10L)
//                .build();
//        ProdOrderDetail prodOrderDetail2 = ProdOrderDetail.builder()
//                .prodOrder(prodOrder)
//                .product(prodOption4)
//                .quantity(20L)
//                .build();
//        em.persist(prodOrderDetail1);
//        em.persist(prodOrderDetail2);
//
//        Delivery delivery1 = Delivery.builder()
//                .prodOrderDetail(prodOrderDetail1)
//                .statusCode(StatusCodeType.DELIVERY_PENDING.getCode())
//                .build();
//        Delivery delivery2 = Delivery.builder()
//                .prodOrderDetail(prodOrderDetail2)
//                .statusCode(StatusCodeType.DELIVERY_PENDING.getCode())
//                .build();
//        em.persist(delivery1);
//        em.persist(delivery2);
//
//        orderRepository.save(prodOrder);
//
//        // when
//
//        // then
//    }

    private String getReason(Long orderId) {
        List<ProdOrderDetail> prodOrderDetails = orderRepository.findById(orderId).get().getProdOrderDetails();
        return prodOrderDetails.get(0).getReason();
    }

    private ProdOrderDetail createOrderDetail_type(String code) {
        return ProdOrderDetail.builder()
                .statusCode(code)
                .build();
    }


}