package com.objects.marketbridge.domain.coupon.repository;

import com.objects.marketbridge.product.coupon.repository.CouponRepository;
import com.objects.marketbridge.product.coupon.repository.MemberCouponRepository;
import com.objects.marketbridge.member.service.port.MemberRepository;
import com.objects.marketbridge.common.infra.entity.CouponEntity;
import com.objects.marketbridge.common.infra.entity.MemberEntity;
import com.objects.marketbridge.common.infra.entity.MemberCouponEntity;
import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
class MemberCouponRepositoryImplTest {

    @Autowired
    MemberCouponRepository memberCouponRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired
    CouponRepository couponRepository;

    @BeforeEach
    void init() {
        MemberEntity member = MemberEntity.builder().email("test@email.com").build();
        memberRepository.save(member);
        CouponEntity coupon = CouponEntity.builder().name("5000원짜리 쿠폰").build();
        couponRepository.save(coupon);
        MemberCouponEntity memberCoupon = MemberCouponEntity.builder().member(member).coupon(coupon).build();
        memberCouponRepository.save(memberCoupon);
    }

    @DisplayName("멤버아이디와 쿠폰아이디로 멤버쿠폰을 가져올수 있다.")
    @Test
    void findByMember_IdAndCoupon_Id(){
        //given
        MemberEntity member = memberRepository.findByEmail("test@email.com").orElseThrow(EntityNotFoundException::new);
        CouponEntity coupon = couponRepository.findAll().get(0);

        //when
        MemberCouponEntity memberCoupon = memberCouponRepository.findByMember_IdAndCoupon_Id(member.getId(), coupon.getId());

        //then
        Assertions.assertThat(memberCoupon.getMember().getEmail()).isEqualTo("test@email.com");
        Assertions.assertThat(memberCoupon.getCoupon().getName()).isEqualTo("5000원짜리 쿠폰");
    }
}