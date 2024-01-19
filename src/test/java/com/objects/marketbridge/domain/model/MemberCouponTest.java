package com.objects.marketbridge.domain.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class MemberCouponTest {
    
    @Test
    @DisplayName("사용여부와 사용시간이 초기화 되어야 한다.")
    public void returnCoupon() {
        // given
        LocalDateTime localDateTime = LocalDateTime.of(2024, 1, 16, 6, 34);
        MemberCoupon usedCoupon = createMemberCoupon(localDateTime, true);

        // when
        usedCoupon.returnCoupon();
    
        // then
        Assertions.assertThat(usedCoupon).extracting("usedDate", "isUsed")
                .containsExactly(null, false);
    }

    private static MemberCoupon createMemberCoupon(LocalDateTime localDateTime, boolean isUsed) {
        return MemberCoupon.builder()
                .usedDate(localDateTime)
                .isUsed(isUsed)
                .build();
    }

}