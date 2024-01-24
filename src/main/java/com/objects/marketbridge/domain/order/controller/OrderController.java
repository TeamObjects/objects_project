package com.objects.marketbridge.domain.order.controller;

import com.objects.marketbridge.domain.member.repository.MemberRepository;
import com.objects.marketbridge.domain.payment.dto.TossAutoPaymentRequest;
import com.objects.marketbridge.model.Address;
import com.objects.marketbridge.model.Member;
import com.objects.marketbridge.domain.order.controller.request.CheckoutRequest;
import com.objects.marketbridge.domain.order.controller.response.CheckoutResponse;
import com.objects.marketbridge.domain.order.dto.CreateOrderDto;
import com.objects.marketbridge.domain.order.service.CreateOrderService;
import com.objects.marketbridge.domain.payment.config.TossPaymentConfig;
import com.objects.marketbridge.global.common.ApiResponse;
import com.objects.marketbridge.global.error.CustomLogicException;
import com.objects.marketbridge.global.security.mock.AuthMemberId;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.objects.marketbridge.global.error.ErrorCode.SHIPPING_ADDRESS_NOT_REGISTERED;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final MemberRepository memberRepository;
    private final TossPaymentConfig tossPaymentConfig;
    private final CreateOrderService createOrderService;

    @GetMapping("/orders/checkout")
    public ApiResponse<CheckoutResponse> getCheckout(
            @AuthMemberId Long memberId) {

//        Long id = memberId;
        Member member = memberRepository.findByIdWithAddresses(memberId).orElseThrow(EntityNotFoundException::new);
        CheckoutResponse checkoutResponse = createOrderResponse(member);

        return ApiResponse.ok(checkoutResponse);
    }

    private CheckoutResponse createOrderResponse(Member member) {

        Address address = filterDefaultAddress(member.getAddresses());

        return CheckoutResponse.from(address.getAddressValue());
    }

    private Address filterDefaultAddress(List<Address> addresses) {

        return addresses.stream()
                .filter(Address::getIsDefault)
                .findFirst()
                .orElseThrow(() -> new CustomLogicException(SHIPPING_ADDRESS_NOT_REGISTERED.getMessage(), SHIPPING_ADDRESS_NOT_REGISTERED));
    }

    @PostMapping("/orders/checkout")
    public ApiResponse<CheckoutRequest> saveOrderTemp(
            @AuthMemberId Long memberId,
            @Valid @RequestBody CheckoutRequest request) {

        request.setSuccessUrl(tossPaymentConfig.getSuccessUrl());
        request.setFailUrl(tossPaymentConfig.getFailUrl());

        CreateOrderDto createOrderDto = request.toDto(memberId);
        createOrderService.create(createOrderDto);

        return ApiResponse.ok(request);
    }
}
