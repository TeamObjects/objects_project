package com.objects.marketbridge.domain.product.repository.category;

import com.objects.marketbridge.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

}
