package com.objects.marketbridge.domain.order.service;

import com.objects.marketbridge.domain.address.repository.AddressRepository;
import com.objects.marketbridge.domain.coupon.repository.CouponRepository;
import com.objects.marketbridge.domain.member.repository.MemberRepository;
import com.objects.marketbridge.model.Address;
import com.objects.marketbridge.model.Coupon;
import com.objects.marketbridge.model.Member;
import com.objects.marketbridge.model.Product;
import com.objects.marketbridge.domain.order.dto.CreateOrderDto;
import com.objects.marketbridge.domain.order.entity.ProdOrder;
import com.objects.marketbridge.domain.order.entity.ProdOrderDetail;
import com.objects.marketbridge.domain.order.entity.ProductValue;
import com.objects.marketbridge.domain.order.entity.StatusCodeType;
import com.objects.marketbridge.domain.order.service.port.OrderDetailRepository;
import com.objects.marketbridge.domain.order.service.port.OrderRepository;
import com.objects.marketbridge.domain.product.repository.ProductJpaRepository;
import com.objects.marketbridge.domain.product.repository.ProductRepository;
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

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CreateOrderServiceTest {

    @Autowired CreateOrderService createOrderService;
    @Autowired ProductRepository productRepository;
    @Autowired ProductJpaRepository productJpaRepository;
    @Autowired CouponRepository couponRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired AddressRepository addressRepository;
    @Autowired OrderDetailRepository orderDetailRepository;
    @Autowired OrderRepository orderRepository;

    @BeforeEach
    void init(){
        // member 생성
        Member member = Member.builder()
                .email("hong@email.com")
                .name("홍길동").build();
        memberRepository.save(member);

        // address 생성
        Address address = Address.builder()
                .member(member)
                .build();
        addressRepository.save(address);

        // product 생성
        Product product1 = Product.builder()
                .name("가방")
                .price(1000L)
                .build();
        Product product2 = Product.builder()
                .name("티비")
                .price(2000L)
                .build();
        Product product3 = Product.builder()
                .name("워치")
                .price(3000L)
                .build();
        productRepository.saveAll(List.of(product1, product2, product3));


        // coupon 생성
        Coupon coupon1 = Coupon.builder()
                .price(2000L)
                .product(product1)
                .name("가방쿠폰")
                .build();

        Coupon coupon2 = Coupon.builder()
                .price(2000L)
                .product(product2)
                .name("티비")
                .build();

        couponRepository.saveAll(List.of(coupon1, coupon2));
    }
    @DisplayName("주문 생성시 ProdOrder 를 생성 한다.")
    @Test
    void CreateProdOrder(){

        //given
        Member member = memberRepository.findByEmail("hong@email.com").orElseThrow(EntityNotFoundException::new);
        Address address = addressRepository.findByMemberId(member.getId()).get(0);
        CreateOrderDto createOrderDto = createDto(member, address);

        //when
        createOrderService.create(createOrderDto);
        ProdOrder order = orderRepository.findByOrderNo(createOrderDto.getOrderNo());

        //then
        assertThat(order.getMember().getId()).isEqualTo(member.getId());
        assertThat(order.getAddress()).isEqualTo(address);
        assertThat(order.getOrderName()).isEqualTo("가방 외 2건");
        assertThat(order.getOrderNo()).isEqualTo("aaaa-aaaa-aaaa");
        assertThat(order.getTotalPrice()).isEqualTo(createOrderDto.getTotalOrderPrice());
        assertThat(order.getRealPrice()).isEqualTo(createOrderDto.getRealOrderPrice());
    }

    private static long getTotalCouponPrice(List<Coupon> coupons) {

        return coupons.stream().mapToLong(Coupon::getPrice).sum();
    }

    private long getTotalOrderPrice(List<ProductValue> productValues) {

        return productValues.stream().mapToLong(p ->
                productRepository.findById(p.getProductId()).getPrice() * p.getQuantity()
        ).sum();
    }

    @DisplayName("주문 생성시 ProdOrderDetail 을 생성 한다.")
    @Test
    void CreateProdOrderDetail(){

        //given
        Member member = memberRepository.findByEmail("hong@email.com").orElseThrow(EntityNotFoundException::new);
        Address address = addressRepository.findByMemberId(member.getId()).get(0);
        CreateOrderDto createOrderDto = createDto(member, address);

        //when
        createOrderService.create(createOrderDto);
        List<ProdOrderDetail> orderDetails = orderDetailRepository.findByOrderNo(createOrderDto.getOrderNo());

        //then
        assertThat(orderDetails).hasSize(3);

        for (int i = 0; i < orderDetails.size(); i++) {
            assertThat(orderDetails.get(i).getProduct()).isEqualTo(productRepository.findById(createOrderDto.getProductValues().get(i).getProductId()));
            assertThat(orderDetails.get(i).getOrderNo()).isEqualTo(createOrderDto.getOrderNo());
            assertThat(orderDetails.get(i).getQuantity()).isEqualTo(createOrderDto.getProductValues().get(i).getQuantity());
            assertThat(orderDetails.get(i).getPrice()).isEqualTo(productRepository.findById(createOrderDto.getProductValues().get(i).getProductId()).getPrice());
            assertThat(orderDetails.get(i).getStatusCode()).isEqualTo(StatusCodeType.ORDER_INIT.getCode());
        }
    }

    @DisplayName("ProdOrder 와 ProdOrderDetail 이 서로 연관관계를 맺어야한다")
    @Test
    void mappingProdOrderWithProdOrderDetail(){

        //given
        Member member = memberRepository.findByEmail("hong@email.com").orElseThrow(EntityNotFoundException::new);
        Address address = addressRepository.findByMemberId(member.getId()).get(0);
        CreateOrderDto createOrderDto = createDto(member, address);

        //when
        createOrderService.create(createOrderDto);
        List<ProdOrderDetail> orderDetails = orderDetailRepository.findByOrderNo(createOrderDto.getOrderNo());
        ProdOrder order = orderRepository.findByOrderNo(createOrderDto.getOrderNo());

        //then
        List<ProdOrderDetail> actualList = order.getProdOrderDetails();
        Assertions.assertThat(actualList).containsExactlyInAnyOrderElementsOf(orderDetails);

        for (ProdOrderDetail orderDetail : actualList) {
            assertThat(orderDetail.getProdOrder()).isEqualTo(order);
        }
    }

    private CreateOrderDto createDto(Member member, Address address) {

        List<Coupon> coupons = couponRepository.findAll();
        List<Product> products = productRepository.findAll();

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
                .quantity(3L)
                .build();

        List<ProductValue> productValues = List.of(productValue1, productValue2, productValue3);

        Long totalOrderPrice = getTotalOrderPrice(productValues);
        Long totalCouponPrice = getTotalCouponPrice(coupons);

        Long realOrderPrice = totalOrderPrice - totalCouponPrice;

        return CreateOrderDto.builder()
                .memberId(member.getId())
                .addressId(address.getId())
                .orderName("가방 외 2건")
                .orderNo("aaaa-aaaa-aaaa")
                .totalOrderPrice(totalOrderPrice)
                .realOrderPrice(realOrderPrice)
                .productValues(productValues)
                .build();
    }

}