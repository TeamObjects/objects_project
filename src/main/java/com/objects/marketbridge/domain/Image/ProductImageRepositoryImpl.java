package com.objects.marketbridge.domain.Image;

import com.objects.marketbridge.model.ProductImage;
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
