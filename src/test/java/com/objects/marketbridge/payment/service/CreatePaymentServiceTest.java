package com.objects.marketbridge.payment.service;

import com.objects.marketbridge.common.domain.AddressValue;
import com.objects.marketbridge.common.domain.Member;
import com.objects.marketbridge.common.domain.Product;
import com.objects.marketbridge.common.dto.KakaoPayApproveResponse;
import com.objects.marketbridge.member.service.port.MemberRepository;
import com.objects.marketbridge.order.domain.Address;
import com.objects.marketbridge.order.domain.Order;
import com.objects.marketbridge.order.domain.OrderDetail;
import com.objects.marketbridge.order.domain.StatusCodeType;
import com.objects.marketbridge.order.service.port.OrderCommendRepository;
import com.objects.marketbridge.order.service.port.OrderQueryRepository;
import com.objects.marketbridge.payment.domain.Amount;
import com.objects.marketbridge.payment.domain.CardInfo;
import com.objects.marketbridge.payment.domain.Payment;
import com.objects.marketbridge.payment.service.port.PaymentRepository;
import com.objects.marketbridge.product.infra.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.objects.marketbridge.order.domain.StatusCodeType.ORDER_INIT;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Slf4j
class CreatePaymentServiceTest {

    @Autowired PaymentRepository paymentRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired ProductRepository productRepository;
    @Autowired OrderCommendRepository orderCommendRepository;
    @Autowired OrderQueryRepository orderQueryRepository;
    @Autowired CreatePaymentService createPaymentService;


    @BeforeEach
    void init() {
        // 1. Member, Address 생성
        Member member = createMember();
        Address address = createAddress();
        member.addAddress(address);
        memberRepository.save(member);

        // 2. Product ,OrderDetail 생성
        List<Product> products = createProducts();
        List<OrderDetail> orderDetails = createOrderDetails();
        products.get(0).addOrderDetail(orderDetails.get(0));
        products.get(1).addOrderDetail(orderDetails.get(1));
        productRepository.saveAll(products);

        // 4. Order 생성
        Order order = createOrder(member, address);
        order.addOrderDetail(orderDetails.get(0));
        order.addOrderDetail(orderDetails.get(1));
        orderCommendRepository.save(order);
    }

    private List<Product> createProducts() {
        Product product1 = Product.builder()
                .price(4000L)
                .name("신발")
                .build();
        Product product2 = Product.builder()
                .price(6000L)
                .name("가방")
                .build();
        return List.of(product1, product2);
    }

    private  Address createAddress() {
        return Address.builder()
                .addressValue(AddressValue.builder().city("서울").build()).build();
    }

    private  Member createMember() {
        return Member.builder()
                .name("홍길동")
                .build();
    }

    private Order createOrder(Member member, Address address) {
        return Order.builder()
                .member(member)
                .address(address)
                .orderName("신발 외 1건")
                .orderNo("1234")
                .totalDiscount(0L)
                .totalPrice(10000L)
                .realPrice(10000L)
                .tid("tid")
                .build();
    }

    private List<OrderDetail> createOrderDetails() {
        OrderDetail orderDetail1 = OrderDetail.builder()
                .orderNo("1234")
                .price(4000L)
                .statusCode(ORDER_INIT.getCode())
                .build();
        OrderDetail orderDetail2 = OrderDetail.builder()
                .orderNo("1234")
                .price(6000L)
                .statusCode(ORDER_INIT.getCode())
                .build();
        return List.of(orderDetail1, orderDetail2);
    }

    @DisplayName("payment가 생성 되어야 한다.")
    @Test
    void create() {
        // given
        KakaoPayApproveResponse response = createKakaoPayApproveResponse();

        // when
        createPaymentService.create(response);
        Payment payment = paymentRepository.findByOrderNo("1234");

        //then
        Assertions.assertThat(payment.getId()).isEqualTo(1L);

    }

    private KakaoPayApproveResponse createKakaoPayApproveResponse() {
        return KakaoPayApproveResponse.builder()
                .aid("aid")
                .tid("tid")
                .cid("cid")
                .sid("sid")
                .partnerOrderId("1234")
                .partnerUserId("partnerUserId")
                .paymentMethodType("카드")
                .orderName("가방")
                .approvedAt(LocalDateTime.of(2023, 12, 03, 12, 20))
                .amount(createAmount())
                .cardInfo(createCardInfo())
                .build();
    }

    private  CardInfo createCardInfo() {
        return CardInfo.builder()
                .cardIssuerName("카카오뱅크")
                .cardPurchaseName("국민은행")
                .cardInstallMonth("0")
                .build();
    }

    private  Amount createAmount() {
        return Amount.builder()
                .totalAmount(10000L)
                .discountAmount(4000L)
                .build();
    }

}