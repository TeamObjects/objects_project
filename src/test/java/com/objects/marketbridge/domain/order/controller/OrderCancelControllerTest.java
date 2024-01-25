package com.objects.marketbridge.domain.order.controller;

import com.objects.marketbridge.RestDocsSupport;
import com.objects.marketbridge.domain.order.controller.request.OrderCancelRequest;
import com.objects.marketbridge.domain.order.controller.response.OrderCancelResponse;
import com.objects.marketbridge.domain.order.controller.response.ProductResponse;
import com.objects.marketbridge.domain.order.controller.response.RefundInfo;
import com.objects.marketbridge.domain.order.dto.OrderCancelServiceDto;
import com.objects.marketbridge.domain.order.service.OrderCancelService;
import com.objects.marketbridge.domain.payment.dto.RefundDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderCancelControllerTest extends RestDocsSupport {

    private final OrderCancelService orderCancelService = mock(OrderCancelService.class);

    @Override
    protected Object initController() {
        return new OrderCancelController(orderCancelService);
    }

    @Test
    @DisplayName("주문 취소하는 API")
    public void cancelOrder() throws Exception {
        // given
        OrderCancelServiceDto request = OrderCancelRequest.builder()
                .orderId(1L)
                .cancelReason("빵빵아! 옥지얌!")
                .build()
                .toServiceRequest();

        given(orderCancelService.orderCancel(any(OrderCancelServiceDto.class), any(LocalDateTime.class)))
                .willReturn(OrderCancelResponse.builder()
                        .orderId(1L)
                        .orderNumber("ORD001")
                        .totalPrice(300L)
                        .cancellationDate(LocalDateTime.of(2024, 1, 18, 12, 26))
                        .refundInfo(RefundInfo.of(
                                RefundDto.builder()
                                        .totalRefundAmount(10000L)
                                        .refundMethod("card")
                                        .refundProcessedAt(LocalDateTime.of(2024, 1, 18, 12, 26))
                                        .build())
                        )
                        .cancelledItems(List.of(
                                        ProductResponse.builder()
                                                .productId(1L)
                                                .name("빵빵이 키링")
                                                .productNo("P123456")
                                                .price(10000L)
                                                .build(),
                                        ProductResponse.builder()
                                                .productId(2L)
                                                .name("옥지얌 키링")
                                                .productNo("P2345667")
                                                .price(20000L)
                                                .build()
                                )
                        )
                        .build()
                );

        // when // then
        mockMvc.perform(
                        post("/orders/cancel-flow")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("order-cancel",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("orderId").type(JsonFieldType.NUMBER)
                                        .description("상품 타입")
                                        .optional(),
                                fieldWithPath("cancelReason").type(JsonFieldType.STRING)
                                        .description("상품 판매상태")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT)
                                        .description("응답 데이터"),
                                fieldWithPath("data.orderId").type(JsonFieldType.NUMBER)
                                        .description("주문 ID"),
                                fieldWithPath("data.orderNumber").type(JsonFieldType.STRING)
                                        .description("주문 번호"),
                                fieldWithPath("data.totalPrice").type(JsonFieldType.NUMBER)
                                        .description("주문 총 가격"),
                                fieldWithPath("data.cancellationDate").type(JsonFieldType.ARRAY)
                                        .description("주문 취소 시간"),
                                fieldWithPath("data.cancelledItems").type(JsonFieldType.ARRAY)
                                        .description("상품 이름"),
                                fieldWithPath("data.cancelledItems[].productId").type(JsonFieldType.NUMBER)
                                        .description("취소된 상품의 제품 ID"),
                                fieldWithPath("data.cancelledItems[].productNo").type(JsonFieldType.STRING)
                                        .description("취소된 상품의 제품 번호"),
                                fieldWithPath("data.cancelledItems[].name").type(JsonFieldType.STRING)
                                        .description("취소된 상품의 이름"),
                                fieldWithPath("data.cancelledItems[].price").type(JsonFieldType.NUMBER)
                                        .description("취소된 상품의 가격"),
                                fieldWithPath("data.refundInfo").type(JsonFieldType.OBJECT)
                                        .description("환불 정보"),
                                fieldWithPath("data.refundInfo.totalRefundAmount").type(JsonFieldType.NUMBER)
                                        .description("환불 총 금액"),
                                fieldWithPath("data.refundInfo.refundMethod").type(JsonFieldType.STRING)
                                        .description("환불 방법"),
                                fieldWithPath("data.refundInfo.refundProcessedAt").type(JsonFieldType.ARRAY)
                                        .description("환불 시간")
                        )
                ));
    }
}
