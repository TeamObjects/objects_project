package com.objects.marketbridge.order.service;

import com.objects.marketbridge.common.infra.entity.*;
import com.objects.marketbridge.product.coupon.repository.CouponRepository;
import com.objects.marketbridge.product.coupon.repository.MemberCouponRepository;
import com.objects.marketbridge.member.service.port.MemberRepository;
import com.objects.marketbridge.order.service.port.OrderDetailRepository;
import com.objects.marketbridge.order.dto.CreateOrderDto;
import com.objects.marketbridge.common.infra.entity.OrderEntity;
import com.objects.marketbridge.common.infra.entity.OrderDetailEntity;
import com.objects.marketbridge.order.domain.ProductValue;
import com.objects.marketbridge.common.domain.enums.StatusCodeType;
import com.objects.marketbridge.order.service.port.OrderRepository;
import com.objects.marketbridge.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CreateOrderService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final CouponUsageService couponUsageService;
    private final ProductStockService productStockService;

    @Transactional
    public void create(CreateOrderDto createOrderDto) {

        // 1. Order 생성
        OrderEntity orderEntity = orderRepository.save(createOrder(createOrderDto));

        // 2. OrderDetail 생성 (연관관계 매핑 여기서 해결)
        List<OrderDetailEntity> orderDetailEntities = orderDetailRepository.saveAll(createOrderDetails(createOrderDto.getProductValues(), orderEntity));

        // 3. Order 에 최종쿠폰사용 금액 집어넣기
        orderEntity.setTotalUsedCouponPrice(getTotalCouponPrice(orderDetailEntities));

        // 4. MemberCoupon 의 isUsed 변경
        List<MemberCouponEntity> memberCoupons = getMemberCoupons(orderDetailEntities, createOrderDto.getMemberId());
        couponUsageService.applyCouponUsage(memberCoupons, true, LocalDateTime.now());

        // 5. Product 의 stock 감소
        productStockService.decrease(orderDetailEntities);
    }

    private List<MemberCouponEntity> getMemberCoupons(List<OrderDetailEntity> orderDetailEntities, Long memberId) {

        return orderDetailEntities.stream()
                .filter(o -> o.getCoupon() != null)
                .map(o ->
                        memberCouponRepository.findByMember_IdAndCoupon_Id(
                                memberId,
                                o.getCoupon().getId())
                ).collect(Collectors.toList());
    }
    private Long getTotalCouponPrice(List<OrderDetailEntity> orderDetailEntities) {

        return orderDetailEntities.stream()
                .filter(o -> o.getCoupon() != null)
                .mapToLong(o -> o.getCoupon().getPrice())
                .sum();
    }

    private OrderEntity createOrder(CreateOrderDto createOrderDto) {

        MemberEntity member = memberRepository.findById(createOrderDto.getMemberId()).orElseThrow(EntityNotFoundException::new);
        AddressEntity addressEntity = memberRepository.findById(createOrderDto.getAddressId());
        String orderName = createOrderDto.getOrderName();
        String orderNo = createOrderDto.getOrderNo();
        Long totalOrderPrice = createOrderDto.getTotalOrderPrice();
        Long realOrderPrice = createOrderDto.getRealOrderPrice();
        String tid = createOrderDto.getTid();

        return OrderEntity.create(member, addressEntity, orderName, orderNo, totalOrderPrice, realOrderPrice, tid);
    }

    private List<OrderDetailEntity> createOrderDetails(List<ProductValue> productValues, OrderEntity orderEntity) {

        List<OrderDetailEntity> orderDetailEntities = new ArrayList<>();

        for (ProductValue productValue : productValues) {

            ProductEntity product = productRepository.findById(productValue.getProductId());
            // 쿠폰이 적용안된 product 가 존재할 경우 그냥 null 저장
            CouponEntity coupon = (productValue.getCouponId() != null) ? couponRepository.findById(productValue.getCouponId()) : null ;
            String orderNo = orderEntity.getOrderNo();
            Long quantity = productValue.getQuantity();
            Long price = product.getPrice();
            String tid = orderEntity.getTid();

            // OrderDetail 엔티티 생성
            OrderDetailEntity orderDetailEntity =
                    OrderDetailEntity.create(tid, orderEntity, product, orderNo, coupon, quantity, price, StatusCodeType.ORDER_INIT.getCode());

            // orderDetails 에 추가
            orderDetailEntities.add(orderDetailEntity);

            // 연관관계 매핑
            orderEntity.addOrderDetail(orderDetailEntity);
        }

        return orderDetailEntities;
    }
}
