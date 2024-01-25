package com.objects.marketbridge.domain.coupon.repository;

import com.objects.marketbridge.model.Coupon;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {

    private final CouponJpaRepository couponJpaRepository;

    @Override
    public Coupon findById(Long id) {
        return couponJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
    @Override
    public List<Coupon> findAllByIds(List<Long> ids) {
        return couponJpaRepository.findAllById(ids);
    }
    @Override
    public void save(Coupon coupon) {
        couponJpaRepository.save(coupon);
    }

    @Override
    public void saveAll(List<Coupon> coupons) {
        couponJpaRepository.saveAll(coupons);
    }

    @Override
    public List<Coupon> findAll() {
        return couponJpaRepository.findAll();
    }
}