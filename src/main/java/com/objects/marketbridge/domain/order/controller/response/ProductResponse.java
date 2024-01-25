package com.objects.marketbridge.domain.order.controller.response;

import com.objects.marketbridge.model.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductResponse {

    private Long productId;
    private String productNo;
    private String name;
    private Long price;

    @Builder
    public ProductResponse(Long productId, String productNo, String name, Long price) {
        this.productId = productId;
        this.productNo = productNo;
        this.name = name;
        this.price = price;
    }

    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
