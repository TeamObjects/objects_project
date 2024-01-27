package com.objects.marketbridge.order.controller.response;

import com.objects.marketbridge.common.infra.entity.OrderDetailEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductInfoResponse {
    
    private Long quantity;
    private String name;
    private Long price;
    // TODO 주문 취소 요청 이미지 반환
    private String image;

    @Builder
    private ProductInfoResponse(Long quantity, String name, Long price, String image) {
        this.quantity = quantity;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public static ProductInfoResponse of(OrderDetailEntity orderDetailEntity) {
        return ProductInfoResponse.builder()
                .quantity(orderDetailEntity.getQuantity())
                .name(orderDetailEntity.getProduct().getName())
                .price(orderDetailEntity.getProduct().getPrice())
                .image(orderDetailEntity.getProduct().getThumbImg())
                .build();
    }

}
