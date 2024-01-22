package com.objects.marketbridge.domain.member.repository;

import com.objects.marketbridge.domain.model.MemberCoupon;

import java.util.List;

public interface MemberCouponRepository {

    List<MemberCoupon> findByMemberId(Long id);
}