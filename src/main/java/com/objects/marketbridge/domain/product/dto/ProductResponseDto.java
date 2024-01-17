package com.objects.marketbridge.domain.product.dto;

import com.objects.marketbridge.domain.model.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponseDto {

    private Long categoryId;
    //    private List<ProdOrderDetail> prodOrderDetails = new ArrayList<>();
    private Boolean isOwn; // 로켓 true , 오픈 마켓 false
    private String name;
    private Long price;
    private Boolean isSubs;
    private Long stock;
    private String thumbImg;
    private Long discountRate;

    @Builder
    public ProductResponseDto(Long categoryId, Boolean isOwn, String name, Long price, Boolean isSubs, Long stock, String thumbImg, Long discountRate) {
        this.categoryId = categoryId;
        this.isOwn = isOwn;
        this.name = name;
        this.price = price;
        this.isSubs = isSubs;
        this.stock = stock;
        this.thumbImg = thumbImg;
        this.discountRate = discountRate;
    }

    //에러나서 기본생성자 임시로 만듦.
    public ProductResponseDto() {
    }
}
