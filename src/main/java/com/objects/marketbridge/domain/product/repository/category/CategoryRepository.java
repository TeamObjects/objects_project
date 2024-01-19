package com.objects.marketbridge.domain.product.repository.category;

import com.objects.marketbridge.domain.model.Category;

public interface CategoryRepository {

    Category findById(Long id);

}
