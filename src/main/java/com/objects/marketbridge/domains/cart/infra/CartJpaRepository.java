package com.objects.marketbridge.domains.cart.infra;

import com.objects.marketbridge.domains.cart.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartJpaRepository extends JpaRepository<Cart, Long> {

    @Query("select c from Cart c where c.product.id = :productId and c.member.id = :memberId")
    Optional<Cart> findByProductIdAndMemberId(@Param("productId") Long productId, @Param("memberId") Long memberId);

    @Query("SELECT COUNT(c) FROM Cart c WHERE c.member.id = :memberId")
    Long countByMemberId(@Param("memberId") Long memberId);
}
