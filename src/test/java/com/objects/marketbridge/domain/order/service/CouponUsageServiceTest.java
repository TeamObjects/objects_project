package com.objects.marketbridge.domain.order.service;

import com.objects.marketbridge.product.coupon.repository.CouponRepository;
import com.objects.marketbridge.product.coupon.repository.MemberCouponRepository;
import com.objects.marketbridge.member.service.port.MemberRepository;
import com.objects.marketbridge.common.infra.entity.CouponEntity;
import com.objects.marketbridge.common.infra.entity.MemberEntity;
import com.objects.marketbridge.common.infra.entity.MemberCouponEntity;
import com.objects.marketbridge.order.service.CouponUsageService;
import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CouponUsageServiceTest {

    @Autowired
    MemberCouponRepository memberCouponRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired CouponRepository couponRepository;
    @Autowired
    CouponUsageService couponUsageService;

    @BeforeEach
    void init() {
        MemberEntity member = MemberEntity.builder().email("test@email.com").build();
        memberRepository.save(member);

        List<CouponEntity> coupons = createCoupons();
        couponRepository.saveAll(coupons);

        List<MemberCouponEntity> memberCoupons = createMemberCoupons(member, coupons);
        memberCouponRepository.saveAll(memberCoupons);
    }


    @Test
    @DisplayName("사용된 쿠폰의 사용 상태와 사용 날짜를 저장한다")
    void applyCouponUsage() {

        //given
        LocalDateTime dateTime = LocalDateTime.now();

        List<CouponEntity> coupons = couponRepository.findAll();
        Long memberId = memberRepository.findByEmail("test@email.com").orElseThrow(EntityNotFoundException::new).getId();

        List<MemberCouponEntity> memberCoupons = getMemberCoupons(coupons, memberId);

        //when
        couponUsageService.applyCouponUsage(memberCoupons, true, dateTime);

        //then
        for (MemberCouponEntity mc : memberCoupons) {
            Assertions.assertThat(mc.getIsUsed()).isTrue();
            Assertions.assertThat(mc.getUsedDate()).isEqualTo(dateTime);
        }
    }

    private List<MemberCouponEntity> getMemberCoupons(List<CouponEntity> coupons, Long memberId) {
        return coupons.stream()
                .map(c ->
                        memberCouponRepository.findByMember_IdAndCoupon_Id(memberId, c.getId()))
                .toList();
    }

    private List<CouponEntity> createCoupons() {

        CouponEntity coupon1 = CouponEntity.builder().name("1000원짜리 쿠폰").build();
        CouponEntity coupon2 = CouponEntity.builder().name("2000원짜리 쿠폰").build();
        CouponEntity coupon3 = CouponEntity.builder().name("3000원짜리 쿠폰").build();

        return List.of(coupon1, coupon2, coupon3);
    }

    private List<MemberCouponEntity> createMemberCoupons(MemberEntity member, List<CouponEntity> coupons) {
        MemberCouponEntity memberCoupon1 = MemberCouponEntity.builder().member(member).coupon(coupons.get(0)).build();
        MemberCouponEntity memberCoupon2 = MemberCouponEntity.builder().member(member).coupon(coupons.get(1)).build();
        MemberCouponEntity memberCoupon3 = MemberCouponEntity.builder().member(member).coupon(coupons.get(2)).build();

        return List.of(memberCoupon1, memberCoupon2, memberCoupon3);
    }
}