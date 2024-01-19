package com.objects.marketbridge.domain.product.dto;

import com.objects.marketbridge.domain.model.Image;
import com.objects.marketbridge.domain.model.Option;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

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

    private List<String> itemImgUrls = new ArrayList<>();
    private List<String> detailImgUrls = new ArrayList<>();

    private Long discountRate;

    private List<String> optionNames = new ArrayList<>();

//    @Builder
//    public ProductResponseDto(Long categoryId, Boolean isOwn, String name, Long price, Boolean isSubs, Long stock, String thumbImg, List<String> itemImgUrls, List<String> detailImgUrls, Long discountRate) {
//        this.categoryId = categoryId;
//        this.isOwn = isOwn;
//        this.name = name;
//        this.price = price;
//        this.isSubs = isSubs;
//        this.stock = stock;
//        this.thumbImg = thumbImg;
//        this.itemImgUrls = itemImgUrls;
//        this.detailImgUrls = detailImgUrls;
//        this.discountRate = discountRate;
//    }

    @Builder
    public ProductResponseDto (Long categoryId, Boolean isOwn, String name, Long price, Boolean isSubs, Long stock, String thumbImg, List<String> itemImgUrls, List<String> detailImgUrls, Long discountRate, List<String> optionNames) {
        this.categoryId = categoryId;
        this.isOwn = isOwn;
        this.name = name;
        this.price = price;
        this.isSubs = isSubs;
        this.stock = stock;
        this.thumbImg = thumbImg;
        this.itemImgUrls = itemImgUrls;
        this.detailImgUrls = detailImgUrls;
        this.discountRate = discountRate;
        this.optionNames = optionNames;
    }


    //에러나서 기본생성자 임시로 만듦.
    public ProductResponseDto() {
    }
}
