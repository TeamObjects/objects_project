package com.objects.marketbridge.domain.coupon.repository;

import com.objects.marketbridge.domain.model.Coupon;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {

    private final CouponJpaRepository couponJpaRepository;

    public Coupon findById(Long id) {
        return couponJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Coupon> findAllByIds(List<Long> ids) {
        return couponJpaRepository.findAllById(ids);
    }

    public void save(Coupon coupon) {
        couponJpaRepository.save(coupon);
    }

}
