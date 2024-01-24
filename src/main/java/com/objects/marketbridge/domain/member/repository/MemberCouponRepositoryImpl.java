package com.objects.marketbridge.domain.member.repository;

import com.objects.marketbridge.model.MemberCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberCouponRepositoryImpl implements MemberCouponRepository{

    private final MemberCouponJpaRepository memberCouponJpaRepository;

    @Override
    public List<MemberCoupon> findByMemberId(Long id) {
        return memberCouponJpaRepository.findByIdWithCoupon(id);
    }
}
