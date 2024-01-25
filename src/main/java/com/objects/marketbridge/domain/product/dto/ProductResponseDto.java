package com.objects.marketbridge.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponseDto {

    private Long productId;

    @Builder
    //에러나서 기본생성자 임시로 만듦.
    public ProductResponseDto() {
    }

    @Builder
    public ProductResponseDto(Long productId) {
        this.productId = productId;
    }


}
