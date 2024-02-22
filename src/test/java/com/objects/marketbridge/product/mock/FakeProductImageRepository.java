package com.objects.marketbridge.product.mock;

import com.objects.marketbridge.domains.product.domain.ProductImage;
import com.objects.marketbridge.domains.product.dto.ProductImageDto;
import com.objects.marketbridge.domains.product.service.port.ProductImageRepository;
import com.objects.marketbridge.domains.product.service.port.ProductImageCustomRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class FakeProductImageRepository implements ProductImageRepository, ProductImageCustomRepository {
    private final AtomicLong autoGeneratedId = new AtomicLong(0);
    List<ProductImage> data = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void save(ProductImage productImage) {
        if (productImage.getId() == null || productImage.getId() == 0){
            data.add(ProductImage.builder()
                    .id(autoGeneratedId.incrementAndGet())
                    .image(productImage.getImage())
                    .imgType(productImage.getImgType())
                    .product(productImage.getProduct())
                    .seqNo(productImage.getSeqNo())
                    .build());
        }else {
            data.removeIf(item -> Objects.equals(item.getId(),productImage.getId()));
            data.add(productImage);
        }
    }

    @Override
    public List<ProductImage> findAllByProductId(Long productId) {
        return null;
    }

    @Override
    public void delete(ProductImage productImage) {

    }

    @Override
    public void saveAll(List<ProductImage> productImages) {
        for (ProductImage productImage: productImages) {
            save(productImage);
        }
    }

    @Override
    public List<ProductImageDto> findAllByProductIdWithImage(Long productId) {
        List<ProductImageDto> result = new ArrayList<>();

        for (ProductImage productImage: data) {
            if (productImage.getProduct().getId() == productId){
                ProductImageDto productImageDto = ProductImageDto.builder()
                        .seqNo(productImage.getSeqNo())
                        .imgUrl(productImage.getImage().getUrl())
                        .type(productImage.getImgType())
                        .build();
                result.add(productImageDto);
            }
        }
        return result;
    }
}
