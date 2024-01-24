package com.objects.marketbridge.domain.point.repository;

import com.objects.marketbridge.model.Point;

public interface PointRepository {
    void save(Point point);
    Point findById(Long id);
    Point findByMemberId(Long memberId);

    void deleteAllInBatch();
}
