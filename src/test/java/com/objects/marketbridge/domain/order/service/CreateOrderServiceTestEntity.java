package com.objects.marketbridge.domain.order.service;

import com.objects.marketbridge.common.infra.entity.*;
import com.objects.marketbridge.product.coupon.repository.CouponRepository;
import com.objects.marketbridge.product.coupon.repository.MemberCouponRepository;
import com.objects.marketbridge.member.service.port.MemberRepository;
import com.objects.marketbridge.common.interceptor.error.CustomLogicException;
import com.objects.marketbridge.common.interceptor.error.ErrorCode;
import com.objects.marketbridge.order.dto.CreateOrderDto;
import com.objects.marketbridge.common.infra.entity.OrderEntity;
import com.objects.marketbridge.common.infra.entity.OrderDetailEntity;
import com.objects.marketbridge.order.domain.ProductValue;
import com.objects.marketbridge.common.domain.enums.StatusCodeType;
import com.objects.marketbridge.order.service.CreateOrderService;
import com.objects.marketbridge.order.service.port.OrderDetailRepository;
import com.objects.marketbridge.order.service.port.OrderRepository;
import com.objects.marketbridge.common.infra.repository.ProductJpaRepository;
import com.objects.marketbridge.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CreateOrderServiceTestEntity {

    @Autowired
    CreateOrderService createOrderService;
    @Autowired ProductRepository productRepository;
    @Autowired ProductJpaRepository productJpaRepository;
    @Autowired CouponRepository couponRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired AddressRepository addressRepository;
    @Autowired OrderDetailRepository orderDetailRepository;
    @Autowired OrderRepository orderRepository;
    @Autowired MemberCouponRepository memberCouponRepository;

    @BeforeEach
    void init(){
        // member 생성
        MemberEntity member = createMember();
        memberRepository.save(member);

        // address 생성
        AddressEntity addressEntity = createAddress(member);
        addressRepository.save(addressEntity);

        // product 생성
        List<ProductEntity> products = createProducts();
        productRepository.saveAll(products);

        // coupon 생성
        List<CouponEntity> coupons = createCoupons(products);
        couponRepository.saveAll(coupons);

        // memberCoupon 생성
        List<MemberCouponEntity> memberCoupons = createMemberCoupons(member, coupons);
        memberCouponRepository.saveAll(memberCoupons);

    }

    private MemberEntity createMember() {

        return MemberEntity.builder()
                .email("hong@email.com")
                .name("홍길동").build();
    }

    private AddressEntity createAddress(MemberEntity member) {

        return AddressEntity.builder()
                .member(member).build();
    }

    private List<ProductEntity> createProducts() {

        ProductEntity product1 = ProductEntity.builder()
                .name("가방")
                .price(1000L)
                .stock(5L).build();

        ProductEntity product2 = ProductEntity.builder()
                .name("티비")
                .stock(5L)
                .price(2000L).build();

        ProductEntity product3 = ProductEntity.builder()
                .name("워치")
                .stock(5L)
                .price(3000L).build();

        return List.of(product1, product2, product3);
    }

    private List<CouponEntity> createCoupons(List<ProductEntity> products) {

        CouponEntity coupon1 = CouponEntity.builder()
                .price(2000L)
                .product(products.get(0))
                .name("가방쿠폰").build();

        CouponEntity coupon2 = CouponEntity.builder()
                .price(2000L)
                .product(products.get(1))
                .name("티비쿠폰").build();

        return List.of(coupon1, coupon2);
    }

    private List<MemberCouponEntity> createMemberCoupons(MemberEntity member, List<CouponEntity> coupons) {

        MemberCouponEntity memberCoupon1 = MemberCouponEntity.builder()
                .member(member)
                .coupon(coupons.get(0))
                .isUsed(false).build();

        MemberCouponEntity memberCoupon2 = MemberCouponEntity.builder()
                .member(member)
                .coupon(coupons.get(1))
                .isUsed(false).build();

        return List.of(memberCoupon1, memberCoupon2);
    }

    @DisplayName("주문 생성시 Order 를 생성 한다.")
    @Test
    void CreateOrder(){

        //given
        MemberEntity member = memberRepository.findByEmail("hong@email.com").orElseThrow(EntityNotFoundException::new);
        AddressEntity addressEntity = addressRepository.findByMemberId(member.getId()).get(0);
        Long defaultQuantity = 3L;
        CreateOrderDto createOrderDto = createDto(member, addressEntity, defaultQuantity);

        //when
        createOrderService.create(createOrderDto);
        OrderEntity orderEntity = orderRepository.findByOrderNo(createOrderDto.getOrderNo());

        //then
        assertThat(orderEntity.getMember().getId()).isEqualTo(member.getId());
        assertThat(orderEntity.getAddressEntity()).isEqualTo(addressEntity);
        assertThat(orderEntity.getOrderName()).isEqualTo("가방 외 2건");
        assertThat(orderEntity.getOrderNo()).isEqualTo("aaaa-aaaa-aaaa");
        assertThat(orderEntity.getTotalPrice()).isEqualTo(createOrderDto.getTotalOrderPrice());
        assertThat(orderEntity.getRealPrice()).isEqualTo(createOrderDto.getRealOrderPrice());
    }

    private long getTotalCouponPrice(List<CouponEntity> coupons) {

        return coupons.stream().mapToLong(CouponEntity::getPrice).sum();
    }

    private long getTotalOrderPrice(List<ProductValue> productValues) {

        return productValues.stream().mapToLong(p ->
                productRepository.findById(p.getProductId()).getPrice() * p.getQuantity()
        ).sum();
    }

    @DisplayName("주문 생성시 OrderDetail 을 생성 한다.")
    @Test
    void CreateOrderDetail(){

        //given
        MemberEntity member = memberRepository.findByEmail("hong@email.com").orElseThrow(EntityNotFoundException::new);
        AddressEntity addressEntity = addressRepository.findByMemberId(member.getId()).get(0);
        Long defaultQuantity = 3L;
        CreateOrderDto createOrderDto = createDto(member, addressEntity, defaultQuantity);

        //when
        createOrderService.create(createOrderDto);
        List<OrderDetailEntity> orderDetailEntities = orderDetailRepository.findByOrderNo(createOrderDto.getOrderNo());

        //then
        assertThat(orderDetailEntities).hasSize(3);

        for (int i = 0; i < orderDetailEntities.size(); i++) {
            assertThat(orderDetailEntities.get(i).getProduct()).isEqualTo(productRepository.findById(createOrderDto.getProductValues().get(i).getProductId()));
            assertThat(orderDetailEntities.get(i).getOrderNo()).isEqualTo(createOrderDto.getOrderNo());
            assertThat(orderDetailEntities.get(i).getQuantity()).isEqualTo(createOrderDto.getProductValues().get(i).getQuantity());
            assertThat(orderDetailEntities.get(i).getPrice()).isEqualTo(productRepository.findById(createOrderDto.getProductValues().get(i).getProductId()).getPrice());
            assertThat(orderDetailEntities.get(i).getStatusCode()).isEqualTo(StatusCodeType.ORDER_INIT.getCode());
        }
    }

    @DisplayName("쿠폰을 사용한 OrderDetail 에는 사용했던 쿠폰이 등록되어있어야한다.")
    @Test
    void OrderDetailWithCoupon(){

        //given
        MemberEntity member = memberRepository.findByEmail("hong@email.com").orElseThrow(EntityNotFoundException::new);
        AddressEntity addressEntity = addressRepository.findByMemberId(member.getId()).get(0);
        Long defaultQuantity = 3L;
        CreateOrderDto createOrderDto = createDto(member, addressEntity, defaultQuantity);
        List<CouponEntity> coupons = couponRepository.findAll();

        //when
        createOrderService.create(createOrderDto);
        List<OrderDetailEntity> orderDetailEntities = orderDetailRepository.findByOrderNo(createOrderDto.getOrderNo());

        //then
        assertThat(orderDetailEntities.get(0).getCoupon().getName()).isEqualTo(coupons.get(0).getName());
        assertThat(orderDetailEntities.get(1).getCoupon().getName()).isEqualTo(coupons.get(1).getName());
        assertThat(orderDetailEntities.get(2).getCoupon()).isNull();
    }

    @DisplayName("Order 와 OrderDetail 이 서로 연관관계를 맺어야한다")
    @Test
    void mappingOrderWithOrderDetail(){

        //given
        MemberEntity member = memberRepository.findByEmail("hong@email.com").orElseThrow(EntityNotFoundException::new);
        AddressEntity addressEntity = addressRepository.findByMemberId(member.getId()).get(0);
        Long defaultQuantity = 3L;
        CreateOrderDto createOrderDto = createDto(member, addressEntity, defaultQuantity);

        //when
        createOrderService.create(createOrderDto);
        List<OrderDetailEntity> orderDetailEntities = orderDetailRepository.findByOrderNo(createOrderDto.getOrderNo());
        OrderEntity orderEntity = orderRepository.findByOrderNo(createOrderDto.getOrderNo());

        //then
        Assertions.assertThat(orderEntity.getOrderDetailEntities()).containsExactlyInAnyOrderElementsOf(orderDetailEntities);

        for (OrderDetailEntity orderDetailEntity : orderDetailEntities) {
            assertThat(orderDetailEntity.getOrderEntity()).isEqualTo(orderEntity);
        }
    }

    @DisplayName("Order에 최종 쿠폰 사용금액이 저장되어야 한다")
    @Test
    void calcualteTotalUsedCouponPrice(){

        //given
        MemberEntity member = memberRepository.findByEmail("hong@email.com").orElseThrow(EntityNotFoundException::new);
        AddressEntity addressEntity = addressRepository.findByMemberId(member.getId()).get(0);
        Long defaultQuantity = 3L;
        CreateOrderDto createOrderDto = createDto(member, addressEntity, defaultQuantity);
        Long totalCouponPrice = couponRepository.findAll().stream().mapToLong(CouponEntity::getPrice).sum();


        //when
        createOrderService.create(createOrderDto);
        OrderEntity orderEntity = orderRepository.findByOrderNo(createOrderDto.getOrderNo());

        //then
        assertThat(orderEntity.getTotalUsedCouponPrice()).isEqualTo(totalCouponPrice);
    }

    @DisplayName("MemberCoupon의 사용상태와 사용날짜를 기록해야한다.")
    @Test
    void memberCouponUsage(){

        //given
        MemberEntity member = memberRepository.findByEmail("hong@email.com").orElseThrow(EntityNotFoundException::new);
        AddressEntity addressEntity = addressRepository.findByMemberId(member.getId()).get(0);
        Long defaultQuantity = 3L;
        CreateOrderDto createOrderDto = createDto(member, addressEntity, defaultQuantity);
        List<MemberCouponEntity> memberCoupons = memberCouponRepository.findAll();

        //when
        createOrderService.create(createOrderDto);

        //then
        assertThat(memberCoupons).hasSize(2);
        assertThat(memberCoupons.get(0).getIsUsed()).isTrue();
        assertThat(memberCoupons.get(1).getIsUsed()).isTrue();
        assertThat(memberCoupons.get(0).getUsedDate()).isNotNull();
        assertThat(memberCoupons.get(1).getUsedDate()).isNotNull();
    }

    @DisplayName("주문 생성시 재고가 감소해야한다.")
    @Test
    void productStockDecrease(){

        //given
        MemberEntity member = memberRepository.findByEmail("hong@email.com").orElseThrow(EntityNotFoundException::new);
        AddressEntity addressEntity = addressRepository.findByMemberId(member.getId()).get(0);
        Long defaultQuantity = 3L;
        CreateOrderDto createOrderDto = createDto(member, addressEntity, defaultQuantity);
        List<ProductEntity> products = productRepository.findAll();
        List<Long> stocks = products.stream().map(ProductEntity::getStock).toList();

        //when
        createOrderService.create(createOrderDto);
        List<Long> quantities = orderDetailRepository.findAll().stream().map(OrderDetailEntity::getQuantity).toList();

        //then
        assertThat(products).hasSize(3);
        for (int i = 0; i < products.size(); i++) {
            assertThat(products.get(i).getStock()).isEqualTo(stocks.get(i)-quantities.get(i));
        }
    }

    @DisplayName("주문량보다 재고가 많을 경우 예외를 발생시켜야한다")
    @Test
    void productStockDecrease_error(){

        //given
        MemberEntity member = memberRepository.findByEmail("hong@email.com").orElseThrow(EntityNotFoundException::new);
        AddressEntity addressEntity = addressRepository.findByMemberId(member.getId()).get(0);
        Long maxQuantity = 100L;
        CreateOrderDto createOrderDto = createDto(member, addressEntity, maxQuantity);

        //when, them
        assertThatThrownBy(() ->
                createOrderService.create(createOrderDto))
                .isInstanceOf(CustomLogicException.class)
                .hasMessageContaining(ErrorCode.OUT_OF_STOCK.getMessage());

    }

    private CreateOrderDto createDto(MemberEntity member, AddressEntity addressEntity, Long lastQuantity) {

        List<CouponEntity> coupons = couponRepository.findAll();
        List<ProductEntity> products = productRepository.findAll();

        ProductValue productValue1 = ProductValue.builder()
                .productId(coupons.get(0).getProduct().getId())
                .couponId(coupons.get(0).getId())
                .quantity(1L)
                .build();

        ProductValue productValue2 = ProductValue.builder()
                .productId(coupons.get(1).getProduct().getId())
                .couponId(coupons.get(1).getId())
                .quantity(2L)
                .build();

        ProductValue productValue3 = ProductValue.builder()
                .productId(products.get(2).getId())
                .quantity(lastQuantity)
                .build();

        List<ProductValue> productValues = List.of(productValue1, productValue2, productValue3);

        Long totalOrderPrice = getTotalOrderPrice(productValues);
        Long totalCouponPrice = getTotalCouponPrice(coupons);

        Long realOrderPrice = totalOrderPrice - totalCouponPrice;

        return CreateOrderDto.builder()
                .memberId(member.getId())
                .addressId(addressEntity.getId())
                .orderName("가방 외 2건")
                .orderNo("aaaa-aaaa-aaaa")
                .totalOrderPrice(totalOrderPrice)
                .realOrderPrice(realOrderPrice)
                .productValues(productValues)
                .build();
    }

}