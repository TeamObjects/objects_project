package com.objects.marketbridge.domain.payment.service;

import com.objects.marketbridge.domain.order.controller.response.TossPaymentsResponse;
import com.objects.marketbridge.domain.order.entity.ProdOrder;
import com.objects.marketbridge.domain.order.entity.ProdOrderDetail;
import com.objects.marketbridge.domain.order.service.port.OrderDetailRepository;
import com.objects.marketbridge.domain.order.service.port.OrderRepository;
import com.objects.marketbridge.domain.payment.domain.BankCode;
import com.objects.marketbridge.domain.payment.domain.Card;
import com.objects.marketbridge.domain.payment.domain.Payment;
import com.objects.marketbridge.domain.payment.service.port.PaymentRepository;
import com.objects.marketbridge.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CreatePaymentServiceTest {

    @Autowired OrderRepository orderRepository;
    @Autowired OrderDetailRepository orderDetailRepository;
    @Autowired ProductRepository productRepository;
    @Autowired CreatePaymentService createPaymentService;
    @Autowired PaymentRepository paymentRepository;

    @BeforeEach
    void init() {

        ProdOrder order = ProdOrder.builder()
                .orderNo("aaaa-aaaa-aaaa")
                .totalPrice(10000L)
                .orderName("홍길동")
                .build();
        orderRepository.save(order);

        ProdOrderDetail orderDetail1 = ProdOrderDetail.builder()
                .orderNo("aaaa-aaaa-aaaa")
                .build();

        ProdOrderDetail orderDetail2 = ProdOrderDetail.builder()
                .orderNo("aaaa-aaaa-aaaa")
                .build();
        List<ProdOrderDetail> orderDetails = List.of(orderDetail1, orderDetail2);

        for (ProdOrderDetail orderDetail : orderDetails) {
            order.addOrderDetail(orderDetail);
        }

        orderDetailRepository.saveAll(orderDetails);

    }

    @DisplayName("Payment 엔티티를 생성해야한다.")
    @Test
    void createPayment(){

        //given
        ProdOrder order = orderRepository.findByOrderNo("aaaa-aaaa-aaaa");
        Card card = getCard("1234-5678", BankCode.HANA.getCode());
        TossPaymentsResponse tossPaymentsResponse =
                getTossPaymentResponse(order.getOrderNo(), "페이먼트키", card);


        //when
        createPaymentService.create(tossPaymentsResponse);

        //then
        Payment payment = paymentRepository.findByOrderNo(order.getOrderNo());

        assertThat(payment.getOrderNo()).isEqualTo("aaaa-aaaa-aaaa");
        assertThat(payment.getPaymentKey()).isEqualTo("페이먼트키");
        assertThat(payment.getCard()).isEqualTo(card);

    }

    private  TossPaymentsResponse getTossPaymentResponse(String orderNo, String paymentKey, Card card) {
        return TossPaymentsResponse.builder()
                .orderId(orderNo)
                .paymentKey(paymentKey)
                .card(card)
                .build();
    }

    private static Card getCard(String cardNo, String cardIssuerCode) {
        return Card.builder()
                .cardNo(cardNo)
                .cardIssuerCode(cardIssuerCode)
                .build();
    }

    @DisplayName("Payment 와 ProdOrder 가 연관관계 매핑이 되어 있어야한다.")
    @Test
    void mappingPaymentWithProdOrder(){
        //given
        ProdOrder order = orderRepository.findByOrderNo("aaaa-aaaa-aaaa");
        Card card = getCard("1234-5678", BankCode.HANA.getCode());
        TossPaymentsResponse tossPaymentsResponse =
                getTossPaymentResponse(order.getOrderNo(), "페이먼트키", card);


        //when
        createPaymentService.create(tossPaymentsResponse);

        //then
        Payment payment = paymentRepository.findByOrderNo("aaaa-aaaa-aaaa");
        assertThat(payment.getProdOrder()).isEqualTo(order);
    }
    
    @DisplayName("ProdOrderDetail 에 paymentKey를 할당해줘야한다.")
    @Test
    void changePaymentKey(){

        //given
        ProdOrder order = orderRepository.findByOrderNo("aaaa-aaaa-aaaa");
        List<ProdOrderDetail> prodOrderDetails = order.getProdOrderDetails();

        Card card = getCard("1234-5678", BankCode.HANA.getCode());
        TossPaymentsResponse tossPaymentsResponse =
                getTossPaymentResponse(order.getOrderNo(), "페이먼트키", card);


        //when
        createPaymentService.create(tossPaymentsResponse);
        
        //then
        assertThat(prodOrderDetails).hasSize(2);
        for (ProdOrderDetail prodOrderDetail : prodOrderDetails) {
            assertThat(prodOrderDetail.getPaymentKey()).isEqualTo(tossPaymentsResponse.getPaymentKey());
        }
    }
}