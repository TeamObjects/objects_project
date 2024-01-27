package com.objects.marketbridge.order.controller.request;

import com.objects.marketbridge.order.dto.CreateOrderDto;
import com.objects.marketbridge.order.domain.ProductValue;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateOrderRequest {

//    @NotNull
    private String orderId;

//    @NotNull
    private Long amount;

//    @NotNull
    private Long addressId;

//    @NotNull
    private String orderName;

//    @NotNull
    private List<ProductValue> productValues;

    @Builder
    public CreateOrderRequest(String orderId, Long amount, Long addressId, String orderName, @Singular List<ProductValue> productValues) {
        this.orderId = orderId;
        this.amount = amount;
        this.addressId = addressId;
        this.orderName = orderName;
        this.productValues = productValues;
    }

    public CreateOrderDto toDto(Long memberId) {
        return CreateOrderDto.builder()
                .memberId(memberId)
                .addressId(addressId)
                .orderName(orderName)
                .orderNo(orderId)
                .totalOrderPrice(amount)
                .productValues(productValues)
                .build();
    }

    public KakaoPayReadyRequest toKakaoReadyRequest(Long memberId, String cid, String approvalUrl, String failUrl, String cancelUrl) {
        return KakaoPayReadyRequest.builder()
                .cid(cid)
                .approvalUrl(approvalUrl)
                .failUrl(failUrl)
                .cancelUrl(cancelUrl)
                .quantity(productValues.stream().mapToLong(ProductValue::getQuantity).sum())
                .partnerOrderId(orderId)
                .partnerUserId(memberId.toString())
                .itemName(orderName)
                .taxFreeAmount(0L)
                .totalAmount(amount)
                .build();
    }
}
