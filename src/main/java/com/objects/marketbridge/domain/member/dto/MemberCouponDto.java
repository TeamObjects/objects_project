package com.objects.marketbridge.domain.member.dto;

import com.objects.marketbridge.domain.model.MemberCoupon;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
public class MemberCouponDto{

    private Long price;
    private String name;
    private Long minimumPrice;
    private LocalDateTime endDate;
    private Boolean isUsed;

    @Builder
    public MemberCouponDto(Long price, String name, Long minimumPrice, LocalDateTime endDate, Boolean isUsed) {
        this.price = price;
        this.name = name;
        this.minimumPrice = minimumPrice;
        this.endDate = endDate;
        this.isUsed = isUsed;
    }


    public static MemberCouponDto from(MemberCoupon memberCoupon) {
        if (memberCoupon.getCoupon() == null) {
            return null; // 또는 기본값이나 예외 처리 등으로 대체할 수 있습니다.
        }
        return MemberCouponDto.builder()
                .endDate(memberCoupon.getCoupon().getEndDate())
                .price(memberCoupon.getCoupon().getPrice())
                .name(memberCoupon.getCoupon().getName())
                .isUsed(memberCoupon.getIsUsed())
                .minimumPrice(memberCoupon.getCoupon().getMinimumPrice())
                .build();
    }

}
