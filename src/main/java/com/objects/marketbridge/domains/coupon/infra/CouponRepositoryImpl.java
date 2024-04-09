package com.objects.marketbridge.domains.coupon.infra;

import com.objects.marketbridge.domains.coupon.domain.Coupon;
import com.objects.marketbridge.domains.coupon.service.port.CouponRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {

    private final CouponJpaRepository couponJpaRepository;

    @Override
    public Coupon findById(Long id) {
        return couponJpaRepository.findById(id).orElseThrow(() -> new JpaObjectRetrievalFailureException(new EntityNotFoundException("해당 엔티티가 존재하지 않습니다. 입력 id = "+id)));
    }

    @Override
    public List<Coupon> findByProductId(Long productId) {
        return couponJpaRepository.findByProductId(productId);
    }

    @Override
    public Coupon save(Coupon coupon) {
        return couponJpaRepository.save(coupon);
    }

    @Override
    public void saveAll(List<Coupon> coupons) {
        couponJpaRepository.saveAll(coupons);
    }

    @Override
    public List<Coupon> findAll() {
        return couponJpaRepository.findAll();
    }

    @Override
    public void deleteAllInBatch() {
        couponJpaRepository.deleteAllInBatch();
    }

    @Override
    public Coupon saveAndFlush(Coupon coupon) {
        return couponJpaRepository.saveAndFlush(coupon);
    }
}
