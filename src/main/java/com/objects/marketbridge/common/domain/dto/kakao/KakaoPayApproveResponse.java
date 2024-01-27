package com.objects.marketbridge.common.domain.dto.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.objects.marketbridge.order.domain.Amount;
import com.objects.marketbridge.order.domain.CardInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class KakaoPayApproveResponse {

    private String aid;
    private String tid;
    private String cid;
    private String sid;

    @JsonProperty("partner_order_id")
    private String partnerOrderId;

    @JsonProperty("partner_user_id")
    private String partnerUserId;

    @JsonProperty("payment_method_type")
    private String paymentMethodType;

    private Amount amount;

    @JsonProperty("card_info")
    private CardInfo cardInfo;

    @JsonProperty("item_name")
    private String orderName;

    @JsonProperty("approved_at")
    private LocalDateTime approvedAt;

    @Builder
    public KakaoPayApproveResponse(String aid, String tid, String cid, String sid, String partnerOrderId, String partnerUserId, String paymentMethodType, Amount amount, CardInfo cardInfo, String orderName, LocalDateTime approvedAt) {
        this.aid = aid;
        this.tid = tid;
        this.cid = cid;
        this.sid = sid;
        this.partnerOrderId = partnerOrderId;
        this.partnerUserId = partnerUserId;
        this.paymentMethodType = paymentMethodType;
        this.amount = amount;
        this.cardInfo = cardInfo;
        this.orderName = orderName;
        this.approvedAt = approvedAt;
    }
}
