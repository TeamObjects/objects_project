package com.objects.marketbridge.domain.point.repository;


import com.objects.marketbridge.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PointJpaRepository extends JpaRepository<Point, Long> {
    Optional<Point> findByMemberId(Long memberId);
}
