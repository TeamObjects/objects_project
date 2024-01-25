package com.objects.marketbridge.domain.category;

import com.objects.marketbridge.model.Category;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository{

    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public Category findById(Long id) {
        return categoryJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
