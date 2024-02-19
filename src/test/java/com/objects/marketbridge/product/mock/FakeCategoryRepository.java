package com.objects.marketbridge.product.mock;

import com.objects.marketbridge.category.domain.Category;
import com.objects.marketbridge.category.service.port.CategoryRepository;
import com.objects.marketbridge.product.domain.Product;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class FakeCategoryRepository implements CategoryRepository {

    private final AtomicLong autoGeneratedId = new AtomicLong(0);
    List<Category> data = Collections.synchronizedList(new ArrayList<>());


    @Override
    public Category findById(Long id) {
        return null;
    }

    @Override
    public void save(Category category) {
        if (category.getId() == null || category.getId() == 0){
            data.add(Category.builder()
                    .id(autoGeneratedId.incrementAndGet())
                    .parentId(category.getParentId())
                    .level(category.getLevel())
                    .name(category.getName())
                    .build());
        } else {
            data.removeIf(item -> Objects.equals(item.getId(), category.getId()));
            data.add(category);
        }
    }

    @Override
    public void saveAll(List<Category> categories) {
        for (Category category: categories) {
            this.save(category);
        }
    }

    @Override
    public Category findByName(String name) {
        return null;
    }

    @Override
    public List<Category> findAllByLevelAndParentIdIsNull(Long level) {
        return null;
    }

    @Override
    public List<Category> findAllByLevelAndParentId(Long level, Long parentId) {
        return null;
    }

    @Override
    public List<Category> findAllByParentId(Long parentId) {
        return null;
    }
}