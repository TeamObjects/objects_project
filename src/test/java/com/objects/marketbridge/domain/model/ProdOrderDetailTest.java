package com.objects.marketbridge.domain.model;

import com.objects.marketbridge.domain.order.entity.ProdOrderDetail;
import com.objects.marketbridge.domain.order.entity.StatusCodeType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.objects.marketbridge.domain.order.entity.StatusCodeType.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

class ProdOrderDetailTest {

    @Test
    @DisplayName("주문 상세의 코드를 바꾼다.")
    public void changeStatusCode() {
        // given
        String givenStatusCode = ORDER_RECEIVED.getCode();
        ProdOrderDetail orderDetail = createOrderDetail(PAYMENT_COMPLETED.getCode());

        // when
        orderDetail.changeStatusCode(givenStatusCode);

        // then
        assertThat(orderDetail.getStatusCode()).isEqualTo(givenStatusCode);
    }

    @Test
    @DisplayName("주문 취소시 취소 이유와 상태코드가 취소로 바뀌어야 한다.")
    public void cancel() {
        // given
        ProdOrderDetail prodOrderDetail = createOrderDetail(ORDER_RECEIVED.getCode());

        // when
        prodOrderDetail.cancel("테스트 취소", ORDER_CANCEL.getCode());

        // then
        assertThat(prodOrderDetail)
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


    private ProdOrderDetail createOrderDetail(String statusCode) {
        return ProdOrderDetail
                .builder()
                .product(Product.builder()
                        .stock(10L)
                        .build())
                .statusCode(statusCode)
                .quantity(10L)
                .build();
    }

}