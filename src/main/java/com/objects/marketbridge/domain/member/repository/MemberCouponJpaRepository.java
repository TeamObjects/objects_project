package com.objects.marketbridge.domain.member.repository;

import com.objects.marketbridge.domain.model.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberCouponJpaRepository extends JpaRepository<MemberCoupon ,Long> {

    @Query("SELECT mc FROM MemberCoupon mc JOIN FETCH mc.coupon c WHERE mc.member.id = :memberId")
    List<MemberCoupon> findByIdWithCoupon(@Param("memberId") Long id);
}
