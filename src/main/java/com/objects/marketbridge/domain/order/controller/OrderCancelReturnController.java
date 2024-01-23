package com.objects.marketbridge.domain.order.controller;

import com.objects.marketbridge.domain.order.controller.request.OrderCancelRequest;
import com.objects.marketbridge.domain.order.controller.response.OrderCancelResponse;
import com.objects.marketbridge.domain.order.controller.response.OrderCancelReturnListResponse;
import com.objects.marketbridge.domain.order.controller.response.OrderCancelReturnResponse;
import com.objects.marketbridge.domain.order.dto.OrderReturnResponse;
import com.objects.marketbridge.domain.order.service.OrderCancelReturnService;
import com.objects.marketbridge.global.common.ApiResponse;
import com.objects.marketbridge.global.security.mock.AuthMemberId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderCancelReturnController {

    private final OrderCancelReturnService orderCancelReturnService;

    @PostMapping("/orders/cancel-return-flow/thank-you")
    public ApiResponse<OrderCancelReturnResponse> cancelReturnOrder(@RequestBody @Valid OrderCancelRequest request) {
        return ApiResponse.ok(orderCancelReturnService.cancelReturnOrder(request.toServiceRequest(), LocalDateTime.now()));
    }

    @GetMapping("/orders/cancel-flow")
    public ApiResponse<OrderCancelResponse> requestCancelOrder(
            @RequestParam(name = "orderId") Long orderId,
            @RequestParam(name = "productIds") List<Long> productIds
    ) {
        return ApiResponse.ok(orderCancelReturnService.requestCancel(orderId, productIds));
    }

    @GetMapping("/orders/return-flow")
    public ApiResponse<OrderReturnResponse> requestReturnOrder(
            @RequestParam(name = "orderId") Long orderId,
            @RequestParam(name = "productIds") List<Long> productIds
    ) {
        return ApiResponse.ok(orderCancelReturnService.requestReturn(orderId, productIds));
    }

    @GetMapping("orders/cancel-return/list")
    public ApiResponse<OrderCancelReturnListResponse> getCancelReturnList(@AuthMemberId Long memberId, Pageable pageable) {
        return ApiResponse.ok(orderCancelReturnService.findCancelReturnList(memberId, pageable));
    }
}