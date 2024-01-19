package com.objects.marketbridge.domain.product.repository.Image;

import com.objects.marketbridge.domain.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageJpaRepository extends JpaRepository<ProductImage, Long> {
}
