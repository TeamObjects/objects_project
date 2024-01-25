package com.objects.marketbridge.domain.order.service;

import com.objects.marketbridge.domain.order.controller.response.TossAutoPaymentsResponse;
import com.objects.marketbridge.domain.order.controller.response.TossErrorResponse;
import com.objects.marketbridge.domain.order.controller.response.TossPaymentsResponse;
import com.objects.marketbridge.domain.order.entity.ProdOrder;
import com.objects.marketbridge.domain.payment.dto.TossAutoPaymentRequest;
import com.objects.marketbridge.global.error.CustomLogicException;
import com.objects.marketbridge.domain.order.service.port.OrderRepository;
import com.objects.marketbridge.domain.payment.config.TossPaymentConfig;
import com.objects.marketbridge.domain.payment.dto.TossConfirmRequest;
import com.objects.marketbridge.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static com.objects.marketbridge.domain.payment.config.TossPaymentConfig.TOSS_BASE_URL;

@Slf4j
@Service
@RequiredArgsConstructor
public class TossApiService {

    private final TossPaymentConfig tossPaymentConfig;
    private final OrderRepository orderRepository;

    public TossPaymentsResponse requestPaymentAccept(TossConfirmRequest request) {

        // 1-1. 데이터 검증
        validPayment(request.getOrderNo(), request.getAmount());

        // 1-2. 결제 요청
        String encodedAuthKey = new String(Base64.getEncoder().encode(tossPaymentConfig.getTestSecretKey().getBytes(StandardCharsets.UTF_8)));

        WebClient tossWebClient = WebClient.builder()
                .baseUrl(TOSS_BASE_URL)
                .defaultHeaders(httpHeaders -> httpHeaders.setBasicAuth(encodedAuthKey))
                .build();

        return tossWebClient
                .post()
                .uri("/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()// 외부 API 호출!
                .onStatus(HttpStatusCode::is4xxClientError,
                        resp -> resp
                                .bodyToMono(TossErrorResponse.class)
                                .flatMap(e ->
                                        Mono.error(new CustomLogicException(e.getCode(), e.getMessage())))
                        )
                .bodyToMono(TossPaymentsResponse.class)
                .block();
                // block() 으로 끝내면 동기적으로 수행됨.
                // API를 여러개 호출하거나 requestPaymentAccept() 이후에 다른 작업이 쌓여있을 경우
                // Mono<T> 로 감싸서 반환하여 비동기작업을 해볼법 하나 현재 그렇지 않기때문에 그냥 block() 으로 끝냄

                // TODO : 결제 실패시 어떻게 처리?

    }

    // Toss 자동결제 TEST
    public TossAutoPaymentsResponse requestAutoPayment(TossAutoPaymentRequest request) {


        String encodedAuthKey = new String(Base64.getEncoder().encode(tossPaymentConfig.getTestSecretKey().getBytes(StandardCharsets.UTF_8)));

        WebClient tossWebClient = WebClient.builder()
                .baseUrl(TOSS_BASE_URL)
                .defaultHeaders(httpHeaders -> httpHeaders.setBasicAuth(encodedAuthKey))
                .build();

        return tossWebClient
                .post()
                .uri("/billing/authorizations/card")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()// 외부 API 호출!
                .onStatus(HttpStatusCode::is4xxClientError,
                        resp -> resp
                                .bodyToMono(TossErrorResponse.class)
                                .flatMap(e ->
                                        Mono.error(new CustomLogicException(e.getCode(), e.getMessage())))
                )
                .bodyToMono(TossAutoPaymentsResponse.class)
                .block();
    }




    private void validPayment(String orderNo, Long totalOrderPrice) {

        ProdOrder order = orderRepository.findByOrderNo(orderNo);
        if (!orderNo.equals(order.getOrderNo()) || !totalOrderPrice.equals(order.getTotalPrice())) {
            throw new CustomLogicException("결제정보가 맞지 않습니다", ErrorCode.MISMATCHED_PAYMENT_DATA.toString());
        }
    }
}
