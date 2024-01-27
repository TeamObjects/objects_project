package com.objects.marketbridge.common.domain.dto.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
public class KakaoPayReadyRequest {

    private String cid;

    @JsonProperty("partner_order_id")
    private String partnerOrderId;

    @JsonProperty("partner_user_id")
    private String partnerUserId;

    @JsonProperty("item_name")
    private String itemName;

    private Long quantity;

    @JsonProperty("total_amount")
    private Long totalAmount;

    @JsonProperty("tax_free_amount")
    private Long taxFreeAmount;

    @JsonProperty("approval_url")
    private String approvalUrl;

    @JsonProperty("cancel_url")
    private String cancelUrl;

    @JsonProperty("fail_url")
    private String failUrl;

    //cid enum 추천
    @Builder
    public KakaoPayReadyRequest(String cid, String partnerOrderId, String partnerUserId, String itemName, Long quantity, Long totalAmount, Long taxFreeAmount, String approvalUrl, String cancelUrl, String failUrl) {
        this.cid = cid;
        this.partnerOrderId = partnerOrderId;
        this.partnerUserId = partnerUserId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.taxFreeAmount = taxFreeAmount;
        this.approvalUrl = approvalUrl+"/"+partnerOrderId;
        this.cancelUrl = cancelUrl;
        this.failUrl = failUrl;
    }

    public MultiValueMap<String, String> toMultiValueMap() {

        MultiValueMap<String, String> requestMap
                = new LinkedMultiValueMap<>();

        requestMap.add("cid", cid);
        requestMap.add("partner_order_id", partnerOrderId);
        requestMap.add("partner_user_id",  partnerUserId);
        requestMap.add("item_name",  itemName);
        requestMap.add("quantity", quantity.toString());
        requestMap.add("total_amount", totalAmount.toString());
        requestMap.add("tax_free_amount", taxFreeAmount.toString());
        requestMap.add("approval_url", approvalUrl);
        requestMap.add("cancel_url", cancelUrl);
        requestMap.add("fail_url", failUrl);

        return requestMap;
    }
}
