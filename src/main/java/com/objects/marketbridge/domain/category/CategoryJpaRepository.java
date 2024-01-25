package com.objects.marketbridge.domain.category;

import com.objects.marketbridge.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

}
