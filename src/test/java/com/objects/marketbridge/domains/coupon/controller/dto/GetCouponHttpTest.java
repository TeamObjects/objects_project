package com.objects.marketbridge.domains.coupon.controller.dto;

import com.objects.marketbridge.domains.coupon.service.dto.GetCouponDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GetCouponHttpTest {

    @DisplayName("GetCouponDto에서 GetCouponHttp.Response.CouponInfo 를 생성 할 수 있다.")
    @Test
    void of(){
        //given
        GetCouponDto dto = GetCouponDto.builder().couponName("1000원짜리 쿠폰").productId(1L).build();

        //when
        GetCouponHttp.Response.CouponInfo result = GetCouponHttp.Response.CouponInfo.of(dto);

        //then
        assertThat(result).extracting(c -> c.getCouponName(), c -> c.getProductId())
                .containsExactly("1000원짜리 쿠폰", 1L);
    }

}