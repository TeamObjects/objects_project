package com.objects.marketbridge.domain.order.service;

import com.objects.marketbridge.domain.member.repository.MemberRepository;
import com.objects.marketbridge.domain.order.controller.response.OrderCancelResponse;
import com.objects.marketbridge.domain.order.dto.OrderCancelServiceDto;
import com.objects.marketbridge.domain.order.entity.ProdOrder;
import com.objects.marketbridge.domain.order.service.port.OrderRepository;
import com.objects.marketbridge.domain.payment.domain.Payment;
import com.objects.marketbridge.domain.payment.dto.RefundDto;
import com.objects.marketbridge.domain.payment.service.RefundService;
import com.objects.marketbridge.domain.payment.service.port.PaymentRepository;
import com.objects.marketbridge.global.error.CustomLogicException;
import com.objects.marketbridge.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.objects.marketbridge.domain.order.entity.StatusCodeType.*;

@Service
@RequiredArgsConstructor
public class OrderCancelService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    private final RefundService refundService;

    // TODO 트랜잭션 위치 고려해야함
    @Transactional
    public OrderCancelResponse orderCancel(OrderCancelServiceDto orderCancelServiceDto, LocalDateTime cancelDateTime) {
        InnerService innerService = new InnerService();

        ProdOrder order = innerService.cancel(
                orderCancelServiceDto.getOrderId(),
                orderCancelServiceDto.getCancelReason(),
                cancelDateTime
        );

        Payment payment = validPayment(orderCancelServiceDto.getOrderId());

        RefundDto refundDto = refundService.refund(
                payment,
                orderCancelServiceDto.getCancelReason(),
                order.getRealPrice()
        );

        return OrderCancelResponse.of(order, refundDto);
    }


    // TODO 객체로 따로 빼야함(임시로 사용)
    class InnerService {

        public ProdOrder cancel(Long orderId, String reason, LocalDateTime cancelDateTime) {
            ProdOrder prodOrder = orderRepository.findProdOrderWithDetailsAndProduct(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("해당하는 주문이 없습니다."));

            prodOrder.cancel(reason, ORDER_CANCEL.getCode(), cancelDateTime);

            prodOrder.returnCoupon();

            return prodOrder;
        }

    }

    private Payment validPayment(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId);
        if (payment == null) {
            throw new CustomLogicException(ErrorCode.PAYMENT_NOT_FOUND.getMessage());
        }
        return payment;
    }

}
