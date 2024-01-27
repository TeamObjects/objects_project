package com.objects.marketbridge.common.domain.dto.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
public class KakaoPayApproveRequest {

    private String cid;

    private String tid;

    @JsonProperty("partner_order_id")
    private String partnerOrderId;

    @JsonProperty("partner_user_id")
    private String partnerUserId;

    @JsonProperty("pg_token")
    private String pgToken;

    @JsonProperty("total_amount")
    private String totalAmount;

    @Builder
    public KakaoPayApproveRequest(String cid, String tid, String partnerOrderId, String partnerUserId, String pgToken, String totalAmount) {
        this.cid = cid;
        this.tid = tid;
        this.partnerOrderId = partnerOrderId;
        this.partnerUserId = partnerUserId;
        this.pgToken = pgToken;
        this.totalAmount = totalAmount;
    }

    public MultiValueMap<String, String> toMultiValueMap() {

        MultiValueMap<String, String> requestMap
                = new LinkedMultiValueMap<>();

        requestMap.add("cid", cid);
        requestMap.add("tid", tid);
        requestMap.add("partner_order_id", partnerOrderId);
        requestMap.add("partner_user_id",  partnerUserId);
        requestMap.add("pg_token",  pgToken);
        requestMap.add("total_amount",  totalAmount);

        return requestMap;
    }

}
