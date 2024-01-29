package com.objects.marketbridge.order.service;



import com.objects.marketbridge.common.domain.Product;
import com.objects.marketbridge.common.exception.error.CustomLogicException;
import com.objects.marketbridge.common.exception.error.ErrorCode;
import com.objects.marketbridge.common.service.port.DateTimeHolder;
import com.objects.marketbridge.order.controller.response.OrderCancelReturnDetailResponse;
import com.objects.marketbridge.order.controller.response.OrderCancelReturnListResponse;
import com.objects.marketbridge.order.domain.Order;
import com.objects.marketbridge.order.domain.OrderDetail;
import com.objects.marketbridge.order.service.dto.CancelResponseDto;
import com.objects.marketbridge.order.service.dto.CancelReturnResponseDto;
import com.objects.marketbridge.order.service.dto.CancelRequestDto;
import com.objects.marketbridge.order.service.dto.OrderReturnResponse;
import com.objects.marketbridge.order.service.port.*;
import com.objects.marketbridge.payment.domain.Payment;
import com.objects.marketbridge.payment.service.dto.RefundDto;
import com.objects.marketbridge.payment.service.port.PaymentRepository;
import com.objects.marketbridge.product.infra.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static com.objects.marketbridge.order.domain.StatusCodeType.ORDER_CANCEL;


@Service
@RequiredArgsConstructor
public class OrderCancelReturnService {

    private final DateTimeHolder dateTimeHolder;

    private final RefundService refundService;

    private final OrderDetailCommendRepository orderDetailCommendRepository;
    private final OrderDetailQueryRepository orderDetailQueryRepository;
    private final OrderCommendRepository orderCommendRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final OrderDtoRepository orderDtoRepository;
    private final PaymentRepository paymentRepository;
    private final ProductRepository productRepository;


    // TODO 트랜잭션 위치 고려해야함
    @Transactional
    public CancelReturnResponseDto cancelReturnOrder(CancelRequestDto cancelRequestDto) {
        InnerService innerService = new InnerService();

        Order order = innerService.cancelReturn(
                cancelRequestDto.getOrderId(),
                cancelRequestDto.getCancelReason(),
                dateTimeHolder.getTimeNow()
        );

        Payment payment = validPayment(cancelRequestDto.getOrderId());

        RefundDto refundDto = refundService.refund(
                payment,
                cancelRequestDto.getCancelReason(),
                order.getRealPrice()
        );

        return CancelReturnResponseDto.of(order, refundDto);
    }

    @Transactional(readOnly = true)
    public CancelResponseDto requestCancel(Long orderId, List<Long> productIds) {
        Order order = orderQueryRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("조회한 주문이 없습니다."));
        List<Product> products = validProducts(productIds);

        List<OrderDetail> orderDetails = orderDetailQueryRepository.findByOrder_IdAndProductIn(orderId, products);

        return CancelResponseDto.of(orderDetails, order);
    }

    @Transactional(readOnly = true)
    public OrderReturnResponse requestReturn(Long orderId, List<Long> productIds) {
        List<Product> products = validProducts(productIds);
        List<OrderDetail> orderDetails = orderDetailQueryRepository.findByOrder_IdAndProductIn(orderId, products);

        return OrderReturnResponse.of(orderDetails);
    }

    @Transactional(readOnly = true)
    public Page<OrderCancelReturnListResponse> findCancelReturnList(Long memberId, Pageable pageable) {
        return orderDtoRepository.findOrdersByMemberId(memberId, pageable);
    }

    @Transactional(readOnly = true)
    public OrderCancelReturnDetailResponse findCancelReturnDetail(String orderNo, Long paymentId, List<Long> productIds) {
        Order order = validOrder(orderNo);
        List<OrderDetail> orderDetails = validOrderDetails(orderNo, productIds);
        Payment payment = vaildPayment(paymentId);

        return OrderCancelReturnDetailResponse.of(order, orderDetails, payment);
    }

    // TODO 객체로 따로 빼야함(임시로 사용)
    class InnerService {
        public Order cancelReturn(Long orderId, String reason, LocalDateTime cancelDateTime) {
            Order order = orderQueryRepository.findOrderWithDetailsAndProduct(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("해당하는 주문이 없습니다."));

            order.cancelReturn(reason, ORDER_CANCEL.getCode(), cancelDateTime);

            order.returnCoupon();

            return order;
        }
    }
    private List<OrderDetail> validOrderDetails(String orderNo, List<Long> productIds) {
        List<OrderDetail> orderDetails = orderDetailQueryRepository.findByOrderNoAndProduct_IdIn(orderNo, productIds);
        if (orderDetails.isEmpty()) {
            throw new EntityNotFoundException("조회된 주문 상세가 없습니다.");
        }
        return orderDetails;
    }

    private Payment vaildPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId);
        if (payment == null) {
            throw new EntityNotFoundException("조회된 결재가 없습니다.");
        }
        return payment;
    }

    private Payment validPayment(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId);
        if (payment == null) {
            throw new CustomLogicException(ErrorCode.PAYMENT_NOT_FOUND.getMessage());
        }
        return payment;
    }

    private List<Product> validProducts(List<Long> productIds) {
        List<Product> products = productRepository.findAllById(productIds);
        if (products == null) {
            throw new NoSuchElementException("조회된 상품이 없습니다.");
        }
        return products;
    }

    private Order validOrder(String orderNo) {
        Order order = orderQueryRepository.findByOrderNo(orderNo);
        if (order == null) {
            throw new EntityNotFoundException("조회된 주문이 없습니다.");
        } return order;
    }

}
