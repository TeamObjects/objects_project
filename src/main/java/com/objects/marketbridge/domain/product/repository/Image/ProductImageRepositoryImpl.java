package com.objects.marketbridge.domain.product.repository.Image;

import com.objects.marketbridge.domain.model.Category;
import com.objects.marketbridge.domain.model.Image;
import com.objects.marketbridge.domain.model.ProductImage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductImageRepositoryImpl implements ProductImageRepository{

    private final ProductImageJpaRepository productImageJpaRepository;

    @Override
    public void save(ProductImage productImage) {
        productImageJpaRepository.save(productImage);
    }

}
