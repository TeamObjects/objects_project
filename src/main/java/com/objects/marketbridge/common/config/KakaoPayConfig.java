package com.objects.marketbridge.common.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class KakaoPayConfig {

    public static final String KAKAO_BASE_URL = "https://kapi.kakao.com/v1/payment";

    public static final String ONE_TIME_CID = "TC0ONETIME";

    public static final String SUBS_CID = "TCSUBSCRIP";

    public static final String KAKAO_AK = "kakaoAK ";

    public static final String READY_END_POINT = "/ready";

    @Value("${payment.kakao.admin_key}")
    public static String ADMIN_KEY;

    @Value("${payment.kakao.test_api_key}")
    public static String TEST_API_KEY;

    @Value("${host}")
    private String host;

    private final String cancelUrl = "/payments/kakao/cancel";

    private final String failUrl = "/payments/kakao/fail";

    private final String approvalUrl = "/payments/kakao/approval";


    public String getRedirectCancelUrl() {
        return host + cancelUrl;
    }

    public String getRedirectFailUrl() {
        return host + failUrl;
    }

    public String createApprovalUrl(String uri) {
        return host+uri+approvalUrl;

    }
}
