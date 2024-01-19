package com.objects.marketbridge.domain.payment.client;

import com.objects.marketbridge.domain.order.controller.response.TossErrorResponse;
import com.objects.marketbridge.domain.order.controller.response.TossPaymentsResponse;
import com.objects.marketbridge.domain.order.service.port.OrderRepository;
import com.objects.marketbridge.domain.payment.config.TossPaymentConfig;
import com.objects.marketbridge.domain.payment.domain.Payment;
import com.objects.marketbridge.domain.payment.dto.RefundInfoDto;
import com.objects.marketbridge.global.error.CustomLogicException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.BindParam;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

import static com.objects.marketbridge.domain.payment.config.TossPaymentConfig.TOSS_BASE_URL;

@Component
@RequiredArgsConstructor
public class PaymentRefundClient implements RefundClient{


    @Override
    public RefundInfoDto refund(Payment paymentKey, String cancelReason, Long cancelAmount) {

        WebClient tossWebClient = WebClient.builder()
                .baseUrl(TOSS_BASE_URL + "payments/")
                .build();

        TossPaymentsResponse response = tossWebClient
                .post()
                .uri(paymentKey + "/cancel")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(Request.create(cancelReason, cancelAmount))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        resp -> resp
                                .bodyToMono(TossErrorResponse.class)
                                .flatMap(e ->
                                        Mono.error(new CustomLogicException(e.getCode(), e.getMessage())))
                )
                .bodyToMono(TossPaymentsResponse.class)
                .block();

        return RefundInfoDto.of(response);
    }

    static class Request {

        private String cancelReason;
        private Long cancelAmount;

        @Builder
        public Request(String cancelReason, Long cancelAmount) {
            this.cancelReason = cancelReason;
            this.cancelAmount = cancelAmount;
        }

        public static Request create(String cancelReason, Long cancelAmount) {
            return Request.builder()
                    .cancelAmount(cancelAmount)
                    .cancelReason(cancelReason)
                    .build();
        }
    }

}
