package com.objects.marketbridge.domain.product.service;

import com.objects.marketbridge.domain.model.Category;
import com.objects.marketbridge.domain.model.Product;
import com.objects.marketbridge.domain.product.dto.ProductDto;
import com.objects.marketbridge.domain.product.dto.ProductRequestDto;
import com.objects.marketbridge.domain.product.dto.ProductResponseDto;
import com.objects.marketbridge.domain.product.repository.ProductRepository;
import com.objects.marketbridge.domain.product.repository.ProductRepositoryImpl;
import com.objects.marketbridge.domain.product.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void registerProduct(ProductRequestDto productRequestDto) {

        Category category = categoryRepository.findById(productRequestDto.getCategoryId());

        // ProductRequestDto에서 필요한 정보 추출하여 Product 엔터티 생성
        Product product = Product.builder()
                .category(category)
                .isOwn(productRequestDto.getIsOwn())
                .name(productRequestDto.getName())
                .price(productRequestDto.getPrice())
                .isSubs(productRequestDto.getIsSubs())
                .stock(productRequestDto.getStock())
                .thumbImg(productRequestDto.getThumbImg())
                .discountRate(productRequestDto.getDiscountRate())
                .build();

        // ProductRepositoryImpl 통해 엔터티를 저장
        productRepository.save(product);

    }

    public void getProductList(){
    }
}
