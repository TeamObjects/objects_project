package com.objects.marketbridge.domain.product.dto;

import com.objects.marketbridge.domain.model.Category;
import com.objects.marketbridge.domain.order.domain.ProdOrderDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ProductDto {

    private Category categoryId;
//    private List<ProdOrderDetail> prodOrderDetails = new ArrayList<>();
    private Boolean isOwn; // 로켓 true , 오픈 마켓 false
    private String name;
    private Long price;
    private Boolean isSubs;
    private Long stock;
    private String thumbImg;
    private Long discountRate;

    @Builder
    public ProductDto(Category categoryId, Boolean isOwn, String name, Long price, Boolean isSubs, Long stock, String thumbImg, Long discountRate) {
        this.categoryId = categoryId;
        this.isOwn = isOwn;
        this.name = name;
        this.price = price;
        this.isSubs = isSubs;
        this.stock = stock;
        this.thumbImg = thumbImg;
        this.discountRate = discountRate;
    }

}
