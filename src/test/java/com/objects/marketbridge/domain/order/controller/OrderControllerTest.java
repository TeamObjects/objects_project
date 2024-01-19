//package com.objects.marketbridge.domain.order.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.objects.marketbridge.domain.member.repository.MemberRepository;
//import com.objects.marketbridge.domain.order.controller.request.CreateOrderRequest;
//import com.objects.marketbridge.domain.order.dto.ProductInfoDto;
//import com.objects.marketbridge.domain.order.service.CreateOrderService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpSession;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
///**
// * @SpringBootTest -> 컨테이너에 모든 빈을 등록하고 주입해줘서 무거움(느림)
// * @WebMvcTest -> 웹 관련 빈만 주입해줌(ex,@Controller) 그래서 가벼움,
// *                 대신 다른 빈들은 @MockBean 으로 등록해줘야함
// */
//@ActiveProfiles("local")
//@WebMvcTest(controllers = OrderController.class)
//class OrderControllerTest {
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//
//
//
//    @MockBean
//    private CreateOrderService createOrderService;
//    @MockBean
//    MemberRepository memberRepository;
//
////    @DisplayName("주문 요청을 하기 전, 먼저 주문서에 들어갈 정보를 보여줘야한다")
////    @Test
////    void showTest() throws Exception {
////
////        // given
////        mockMvc.perform(MockMvcRequestBuilders.get("/orders/checkout"))
////                .andExpect(MockMvcResultMatchers.status().isOk());
////        // when
////
////        //then
////
////    }
//
//    @DisplayName("신규 주문을 생성한다")
//    @Test
//    void createOrder() throws Exception {
//
//        MockHttpSession session = new MockHttpSession();
//        session.setAttribute("memberId", 1L);
//
//        CreateOrderRequest request = CreateOrderRequest.builder()
//                .orderName("가방외 1건")
//                .totalOrderPrice(20000L)
//                .product(ProductInfoDto.builder()
//                        .productId(1L)
//                        .quantity(5L)
//                        .unitOrderPrice(2000L).build())
//                .product(ProductInfoDto.builder()
//                        .productId(2L)
//                        .quantity(10L)
//                        .unitOrderPrice(1000L).build())
//                .build();
//
//        // given
//        mockMvc.perform(MockMvcRequestBuilders
//                    .post("/orders")
//                    .session(session)
//                    .content(objectMapper.writeValueAsString(request))
//                    .contentType(MediaType.APPLICATION_JSON)
//        )
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//        // when
//
//        //then
//
//    }
//
//}