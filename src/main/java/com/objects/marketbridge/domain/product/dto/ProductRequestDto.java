package com.objects.marketbridge.domain.product.dto;

import com.objects.marketbridge.domain.model.Category;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductRequestDto {

    @NotNull
    private Long categoryId;
    //    private List<ProdOrderDetail> prodOrderDetails = new ArrayList<>();
    @NotNull
    private Boolean isOwn; // 로켓 true , 오픈 마켓 false
    @NotNull
    private String name;
    @NotNull
    private Long price;
    @NotNull
    private Boolean isSubs;
    @NotNull
    private Long stock;
    @NotNull
    private String thumbImg;
    @NotNull
    private Long discountRate;

    @Builder
    public ProductRequestDto(Long categoryId, Boolean isOwn, String name, Long price, Boolean isSubs, Long stock, String thumbImg, Long discountRate) {
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
