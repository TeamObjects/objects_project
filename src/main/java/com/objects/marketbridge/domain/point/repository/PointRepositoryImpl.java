package com.objects.marketbridge.domain.point.repository;

import com.objects.marketbridge.domain.model.Point;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PointRepositoryImpl implements PointRepository {

    private final PointJpaRepository pointJpaRepository;

    @Override
    public void save(Point point) {
        pointJpaRepository.save(point);
    }

    @Override
    public Point findById(Long id) {
        return pointJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void deleteAllInBatch() {
        pointJpaRepository.deleteAllInBatch();
    }

    @Override
    public Point findByMemberId(Long memberId) {
        return pointJpaRepository.findByMemberId(memberId).orElseThrow(EntityNotFoundException::new);
    }
}
