package com.objects.marketbridge.domains.coupon.controller.dto;

import com.objects.marketbridge.domains.coupon.service.dto.CreateCouponDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CreateCouponHttpTest {

    @DisplayName("CreateCouponHttp.Request -> CreateCouponDto 로 변환할 수 있다")
    @Test
    void toDto(){
        //given
        CreateCouponHttp.Request request = CreateCouponHttp.Request.builder()
                .price(1000L)
                .name("1000원 할인 쿠폰")
                .count(9999L)
                .productGroupId(111111L)
                .startDate(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .endDate(LocalDateTime.of(2025, 1, 1, 12, 0, 0))
                .minimumPrice(15000L)
                .build();

        //when
        CreateCouponDto dto = request.toDto();

        //then
        assertThat(dto).hasFieldOrPropertyWithValue("price", 1000L);
        assertThat(dto).hasFieldOrPropertyWithValue("name", "1000원 할인 쿠폰");
        assertThat(dto).hasFieldOrPropertyWithValue("count", 9999L);
        assertThat(dto).hasFieldOrPropertyWithValue("productGroupId", 111111L);
        assertThat(dto).hasFieldOrPropertyWithValue("startDate", LocalDateTime.of(2024, 1, 1, 12, 0, 0));
        assertThat(dto).hasFieldOrPropertyWithValue("endDate", LocalDateTime.of(2025, 1, 1, 12, 0, 0));
        assertThat(dto).hasFieldOrPropertyWithValue("minimumPrice", 15000L);
    }

}