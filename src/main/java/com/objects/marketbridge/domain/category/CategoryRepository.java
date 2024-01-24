package com.objects.marketbridge.domain.category;

import com.objects.marketbridge.model.Category;

public interface CategoryRepository {

    Category findById(Long id);

}
