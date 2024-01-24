package com.objects.marketbridge.domain.Image;

import com.objects.marketbridge.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageJpaRepository extends JpaRepository<ProductImage, Long> {
}
