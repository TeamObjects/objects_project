package com.objects.marketbridge.domain.model;

import com.objects.marketbridge.common.infra.entity.OrderDetailEntity;
import com.objects.marketbridge.common.infra.entity.ProductEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.objects.marketbridge.common.domain.enums.StatusCodeType.*;
import static org.assertj.core.api.Assertions.*;

class OrderEntityDetailTest {

    @Test
    @DisplayName("주문 상세의 코드를 바꾼다.")
    public void changeStatusCode() {
        // given
        String givenStatusCode = ORDER_RECEIVED.getCode();
        OrderDetailEntity orderDetailEntity = createOrderDetail(PAYMENT_COMPLETED.getCode());

        // when
        orderDetailEntity.changeStatusCode(givenStatusCode);

        // then
        assertThat(orderDetailEntity.getStatusCode()).isEqualTo(givenStatusCode);
    }

    @Test
    @DisplayName("주문 취소시 취소 이유와 상태코드가 취소로 바뀌어야 한다.")
    public void cancel() {
        // given
        OrderDetailEntity orderDetailEntity = createOrderDetail(ORDER_RECEIVED.getCode());

        // when
        orderDetailEntity.cancel("테스트 취소", ORDER_CANCEL.getCode());

        // then
        assertThat(orderDetailEntity)
                .extracting("statusCode", "reason")
                .contains(ORDER_CANCEL.getCode(), "테스트 취소");

        // then
//        Assertions.assertThat(products).hasSize(2)
//                .extracting("productNumber", "name", "sellingStatus")
//                .containsExactlyInAnyOrder(
//                        tuple("001", "아메리카노", SELLING),
//                        tuple("002", "카페라떼", HOLD)
//                );
    }


    private OrderDetailEntity createOrderDetail(String statusCode) {
        return OrderDetailEntity
                .builder()
                .product(ProductEntity.builder()
                        .stock(10L)
                        .build())
                .statusCode(statusCode)
                .quantity(10L)
                .build();
    }

}