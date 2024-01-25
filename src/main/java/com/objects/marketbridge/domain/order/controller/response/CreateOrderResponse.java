package com.objects.marketbridge.domain.order.controller.response;

import com.objects.marketbridge.domain.order.dto.CreateProdOrderDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateOrderResponse {
    private Long totalOrderPrice;
    private String orderName;
    private String orderNo;
    private String email;
    private String successUrl;
    private String failUrl;

    @Builder
    public CreateOrderResponse(Long totalOrderPrice, String orderName, String orderNo,  String email, String successUrl, String failUrl) {
        this.totalOrderPrice = totalOrderPrice;
        this.orderName = orderName;
        this.orderNo = orderNo;
        this.email = email;
        this.successUrl = successUrl;
        this.failUrl = failUrl;
    }

    public static CreateOrderResponse from(CreateProdOrderDto prodOrderDto, String email, String successUrl, String failUrl){
        return CreateOrderResponse.builder()
                .email(email)
                .orderName(prodOrderDto.getOrderName())
                .totalOrderPrice(prodOrderDto.getTotalOrderPrice())
                .orderNo(prodOrderDto.getOrderNo())
                .successUrl(successUrl)
                .failUrl(failUrl)
                .build();
    }
}
