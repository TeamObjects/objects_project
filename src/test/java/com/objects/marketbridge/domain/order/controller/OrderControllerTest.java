package com.objects.marketbridge.domain.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.objects.marketbridge.domain.member.repository.MemberRepository;
import com.objects.marketbridge.model.Member;
import com.objects.marketbridge.domain.order.controller.request.CheckoutRequest;
import com.objects.marketbridge.domain.order.entity.ProductValue;
import com.objects.marketbridge.domain.order.service.CreateOrderService;
import com.objects.marketbridge.domain.payment.config.TossPaymentConfig;
import com.objects.marketbridge.global.security.mock.WithMockCustomUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @SpringBootTest -> 컨테이너에 모든 빈을 등록하고 주입해줘서 무거움(느림)
 * @WebMvcTest -> 웹 관련 빈만 주입해줌(ex,@Controller) 그래서 가벼움,
 *                 대신 다른 빈들은 @MockBean 으로 등록해줘야함
 */
@ActiveProfiles("test")
@Slf4j
@AutoConfigureMockMvc
@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean TossPaymentConfig tossPaymentConfig;
    @MockBean MemberRepository memberRepository;
    @MockBean CreateOrderService createOrderService;

    @BeforeEach
    void init() {
        Member member = Member.builder()
                .email("member2@example.com")
                .password("1234")
                .name("홍길동")
                .build();
        memberRepository.save(member);
    }

    @WithMockCustomUser(username = "member2@example.com")
    @DisplayName("신규 주문을 생성한다")
    @Test
    void createOrder() throws Exception {

        // given
        List<ProductValue> productValues = createProductValues();
        CheckoutRequest request = createCheckoutRequest(productValues);

//        given(tossPaymentConfig.getSuccessUrl()).willReturn("/success");
//        given(tossPaymentConfig.getFailUrl()).willReturn("/fail");

        // when,then
         mockMvc.perform(MockMvcRequestBuilders
                .post("/orders/checkout")
                 .with(csrf())
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                 .andDo(print());




    }

    private CheckoutRequest createCheckoutRequest(List<ProductValue> productValues) {
        return CheckoutRequest.builder()
                .addressId(1L)
                .orderId("aaaa-aaaa-aaaa")
                .orderName("가방외 1건")
                .amount(20000L)
                .productValues(productValues)
                .build();
    }

    private List<ProductValue> createProductValues() {
        ProductValue productValue1 = ProductValue.builder()
                .deliveredDate("2024-01-21")
                .couponId(1L)
                .productId(1L)
                .quantity(1L).build();

        ProductValue productValue2 = ProductValue.builder()
                .deliveredDate("2024-01-21")
                .couponId(2L)
                .productId(2L)
                .quantity(2L).build();

        return List.of(productValue1, productValue2);
    }

}