package com.objects.marketbridge.order.controller.response;

import com.objects.marketbridge.common.infra.entity.OrderDetailEntity;
import com.objects.marketbridge.common.infra.entity.ProductEntity;
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
    private Long quantity;

    @Builder
    private ProductResponse(Long productId, String productNo, String name, Long price,Long quantity) {
        this.productId = productId;
        this.productNo = productNo;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public static ProductResponse of(ProductEntity product, Long quantity) {
        return ProductResponse.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(quantity)
                .build();
    }

    public static ProductResponse of(OrderDetailEntity orderDetailEntity) {
        return ProductResponse.builder()
                .productId(orderDetailEntity.getProduct().getId())
                .productNo(orderDetailEntity.getProduct().getProductNo())
                .name(orderDetailEntity.getProduct().getName())
                .price(orderDetailEntity.getProduct().getPrice())
                .quantity(orderDetailEntity.getQuantity())
                .build();
    }
}
