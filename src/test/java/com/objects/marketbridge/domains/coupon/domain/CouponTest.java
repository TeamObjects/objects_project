package com.objects.marketbridge.domains.coupon.domain;

import com.objects.marketbridge.domains.member.domain.Member;
import com.objects.marketbridge.domains.product.domain.Product;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


class CouponTest {

    @DisplayName("Coupon 을 생성 할 수 있다.")
    @Test
    void create (){
        //given
        String name = "1000원 쿠폰";
        Long price = 1000L;
        Long count = 10L;
        Long minimumPrice = 10000L;
        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 12, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2025, 1, 1, 12, 0, 0);

        //when
        Coupon coupon = Coupon.create(name, price, count, minimumPrice, startDate, endDate);

        //then
        assertThat(coupon)
                .extracting(
                        Coupon::getName,
                        Coupon::getPrice,
                        Coupon::getCount,
                        Coupon::getMinimumPrice,
                        Coupon::getStartDate,
                        Coupon::getEndDate)
                .contains(name, price, count, minimumPrice, startDate, endDate);
    }
    @DisplayName("ProductNo 의 prefix 만 잘라낼 수 있다")
    @Test
    void parseProductGroupId(){
        //given
        Product product = Product.builder().productNo("111111 - 111111").build();
        Coupon coupon = Coupon.builder().build();

        //when
        product.addCoupons(coupon);

        //then
        assertThat(coupon.getProductGroupId()).isEqualTo(111111L);
    }

    @DisplayName("특정 멤버가 가지고 있는 쿠폰만을 필터링 할 수 있다")
    @Test
    void filteredBy_memberId() {
        // given
        Member member1 = Member.builder().id(1L).build();
        Member member2 = Member.builder().id(2L).build();

        MemberCoupon memberCoupon = MemberCoupon.builder().member(member1).isUsed(false).build();

        Coupon coupon = Coupon.builder().price(1000L).build();
        coupon.addMemberCoupon(memberCoupon);

        // when
        Boolean result1 = coupon.filteredBy(member1.getId());
        Boolean result2 = coupon.filteredBy(member2.getId());

        //then
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

    @DisplayName("이미 사용한 쿠폰은 조회 할 수 없다.")
    @Test
    void filteredBy_isUsed() {
        // given
        Member member1 = Member.builder().id(1L).build();

        MemberCoupon memberCoupon = MemberCoupon.builder().member(member1).isUsed(true).build();

        Coupon coupon = Coupon.builder().price(1000L).build();
        coupon.addMemberCoupon(memberCoupon);

        // when
        Boolean result1 = coupon.filteredBy(member1.getId());

        //then
        assertThat(result1).isFalse();
    }

    @DisplayName("Coupon 과 MemberCoupon 을 연관관계를 맺어준다")
    @Test
    void addMemberCoupon() {
        // given
        Member member1 = Member.builder().id(1L).build();
        Member member2 = Member.builder().id(2L).build();
        Member member3 = Member.builder().id(3L).build();

        Coupon coupon = Coupon.builder().price(1000L).build();

        MemberCoupon memberCoupon1 = MemberCoupon.builder().member(member1).build();
        MemberCoupon memberCoupon2 = MemberCoupon.builder().member(member2).build();
        MemberCoupon memberCoupon3 = MemberCoupon.builder().member(member3).build();

        // when
        coupon.addMemberCoupon(memberCoupon1);
        coupon.addMemberCoupon(memberCoupon2);
        coupon.addMemberCoupon(memberCoupon3);

        //then
        assertThat(coupon.getMemberCoupons()).hasSize(3);

        assertThat(coupon.getMemberCoupons())
                .extracting(MemberCoupon::getCoupon, MemberCoupon::getMember)
                .contains(
                        Tuple.tuple(coupon, member1),
                        Tuple.tuple(coupon, member2),
                        Tuple.tuple(coupon, member3)
                );

        assertThat(memberCoupon1.getCoupon()).isEqualTo(coupon);
        assertThat(memberCoupon2.getCoupon()).isEqualTo(coupon);
        assertThat(memberCoupon3.getCoupon()).isEqualTo(coupon);
    }

    @DisplayName("중복해서 연관 관계를 맺는 다면, 기존 것을 덮어씌워 버린다")
    @Test
    void addMemberCoupon_duplicated() {
        // given
        Member member = Member.builder().id(1L).build();

        Coupon coupon = Coupon.builder().price(1000L).build();

        MemberCoupon memberCoupon = MemberCoupon.builder().member(member).build();

        // when
        coupon.addMemberCoupon(memberCoupon);
        coupon.addMemberCoupon(memberCoupon);
        coupon.addMemberCoupon(memberCoupon);
        coupon.addMemberCoupon(memberCoupon);

        //then
        assertThat(coupon.getMemberCoupons())
                .hasSize(1);

        assertThat(coupon.getMemberCoupons())
                .extracting(MemberCoupon::getCoupon, MemberCoupon::getMember)
                .contains(
                        Tuple.tuple(coupon, member)
                );

        assertThat(memberCoupon.getCoupon()).isEqualTo(coupon);
    }
}