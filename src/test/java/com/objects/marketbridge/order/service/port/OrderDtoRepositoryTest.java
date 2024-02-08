package com.objects.marketbridge.order.service.port;

import com.objects.marketbridge.member.domain.AddressValue;
import com.objects.marketbridge.member.domain.Member;
import com.objects.marketbridge.member.service.port.MemberRepository;
import com.objects.marketbridge.order.controller.dto.GetOrderHttp;
import com.objects.marketbridge.order.domain.Address;
import com.objects.marketbridge.order.domain.Order;
import com.objects.marketbridge.order.domain.OrderDetail;
import com.objects.marketbridge.order.infra.dtio.GetCancelReturnListDtio;
import com.objects.marketbridge.order.service.dto.OrderDto;
import com.objects.marketbridge.product.domain.Product;
import com.objects.marketbridge.product.infra.CouponRepository;
import com.objects.marketbridge.product.infra.ProductRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.objects.marketbridge.order.domain.StatusCodeType.*;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
@Slf4j
class OrderDtoRepositoryTest {

    @Autowired
    OrderCommendRepository orderCommendRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    OrderDtoRepository orderDtoRepository;
    @Autowired
    CouponRepository couponRepository;
    @Autowired
    EntityManager entityManager;

    @DisplayName("전체 주문 목록을 조회 할 경우 현재 사용자의 전체 주문 정보를 알 수 있다.")
    @Test
    void findByMemberIdWithMemberAddress(){

        //given
        Member member = createMember("1");
        Address address = createAddress("서울", "세종대로", "민들레아파트");
        member.addAddress(address);
        memberRepository.save(member);

        Product product1 = createProduct(1000L, "1");
        Product product2 = createProduct(2000L, "2");
        Product product3 = createProduct(3000L, "3");
        Product product4 = createProduct(4000L, "4");
        productRepository.saveAll(List.of(product1, product2, product3, product4));

        OrderDetail orderDetail1 = createOrderDetail(product1, 1L, "1");
        OrderDetail orderDetail2 = createOrderDetail(product2, 1L, "1");
        OrderDetail orderDetail3 = createOrderDetail(product3, 1L, "1");

        OrderDetail orderDetail4 = createOrderDetail(product1, 2L, "2");
        OrderDetail orderDetail5 = createOrderDetail(product2, 2L, "2");
        OrderDetail orderDetail6 = createOrderDetail(product4, 2L, "2");

        OrderDetail orderDetail7 = createOrderDetail(product1, 3L, "3");
        OrderDetail orderDetail8 = createOrderDetail(product3, 3L, "3");
        OrderDetail orderDetail9 = createOrderDetail(product4, 3L, "3");

        OrderDetail orderDetail10 = createOrderDetail(product2, 4L, "4");
        OrderDetail orderDetail11 = createOrderDetail(product3, 4L, "4");
        OrderDetail orderDetail12 = createOrderDetail(product4, 4L, "4");

        Order order1 = createOrder(member, address, "1", List.of(orderDetail1, orderDetail2, orderDetail3));
        Order order2 = createOrder(member, address, "2", List.of(orderDetail4, orderDetail5, orderDetail6));
        Order order3 = createOrder(member, address, "3", List.of(orderDetail7, orderDetail8, orderDetail9));
        Order order4 = createOrder(member, address, "4", List.of(orderDetail10, orderDetail11, orderDetail12));
        orderCommendRepository.saveAll(List.of(order1, order2, order3, order4));

        PageRequest page = PageRequest.of(0, 100);

        GetOrderHttp.Condition condition1
                = createCondition(member.getId(), null, null);
        GetOrderHttp.Condition condition2
                = createCondition(member.getId(), null, "1998");
        GetOrderHttp.Condition condition3
                = createCondition(member.getId(), null, String.valueOf(LocalDateTime.now().getYear()));

        //when
        Page<OrderDto> orders1 = orderDtoRepository.findByMemberIdWithMemberAddress(condition1, page);
        List<OrderDto> contents1 = orders1.getContent();
        Page<OrderDto> orders2 = orderDtoRepository.findByMemberIdWithMemberAddress(condition2, page);
        List<OrderDto> contents2 = orders2.getContent();
        Page<OrderDto> orders3 = orderDtoRepository.findByMemberIdWithMemberAddress(condition3, page);
        List<OrderDto> contents3 = orders3.getContent();

        //then
        // condition 1
        assertThat(contents1).hasSize(4);
        assertThat(contents1.get(0)).extracting("memberId", "orderNo").containsExactlyInAnyOrder(member.getId(), "1");
        assertThat(contents1.get(0).getAddress()).extracting("city", "street", "detail").containsExactlyInAnyOrder("서울", "세종대로", "민들레아파트");

        assertThat(contents1.get(0).getOrderDetails()).hasSize(3);
        assertThat(contents1.get(0).getOrderDetails().get(0)).extracting("quantity", "orderNo").containsExactlyInAnyOrder(1L, "1");
        assertThat(contents1.get(0).getOrderDetails().get(1).getProduct()).extracting("price", "thumbImg", "name").containsExactlyInAnyOrder(2000L, "썸네일2", "상품2");

        assertThat(contents1.get(2).getOrderDetails().get(0)).extracting("quantity", "orderNo").containsExactlyInAnyOrder(3L, "3");
        assertThat(contents1.get(2).getOrderDetails().get(1).getProduct()).extracting("price", "thumbImg", "name").containsExactlyInAnyOrder(3000L, "썸네일3", "상품3");

        // condtion 2
        assertThat(contents2).hasSize(0);

        // condtion 3
        assertThat(contents3).hasSize(4);
        assertThat(contents1.get(2).getOrderDetails().get(0)).extracting("quantity", "orderNo").containsExactlyInAnyOrder(3L, "3");
        assertThat(contents1.get(2).getOrderDetails().get(1).getProduct()).extracting("price", "thumbImg", "name").containsExactlyInAnyOrder(3000L, "썸네일3", "상품3");




    }

    private Address createAddress(String city, String street, String detail) {
        return Address.builder().addressValue(AddressValue.builder()
                .city(city)
                .street(street)
                .detail(detail).build())
                .build();
    }

    @DisplayName("전체 주문 목록을 조회 할 경우 페이징이 가능하다")
    @Test
    void findByMemberIdWithMemberAddress_paging(){

        //given
        Member member = createMember("1");
        Address address = createAddress("서울", "세종대로", "민들레아파트");
        member.addAddress(address);
        memberRepository.save(member);

        Product product1 = createProduct(1000L, "1");
        Product product2 = createProduct(2000L, "2");
        Product product3 = createProduct(3000L, "3");
        Product product4 = createProduct(4000L, "4");
        productRepository.saveAll(List.of(product1, product2, product3, product4));

        OrderDetail orderDetail1 = createOrderDetail(product1,  1L, "1");
        OrderDetail orderDetail2 = createOrderDetail(product2,  1L, "1");
        OrderDetail orderDetail3 = createOrderDetail(product3,  1L, "1");

        OrderDetail orderDetail4 = createOrderDetail(product1,  2L, "2");
        OrderDetail orderDetail5 = createOrderDetail(product2,  2L, "2");
        OrderDetail orderDetail6 = createOrderDetail(product4,  2L, "2");

        OrderDetail orderDetail7 = createOrderDetail(product1,  3L, "3");
        OrderDetail orderDetail8 = createOrderDetail(product3,  3L, "3");
        OrderDetail orderDetail9 = createOrderDetail(product4,  3L, "3");

        OrderDetail orderDetail10 = createOrderDetail(product2, 4L, "4");
        OrderDetail orderDetail11 = createOrderDetail(product3, 4L, "4");
        OrderDetail orderDetail12 = createOrderDetail(product4, 4L, "4");

        Order order1 = createOrder(member, address, "1", List.of(orderDetail1, orderDetail2, orderDetail3));
        Order order2 = createOrder(member, address, "2", List.of(orderDetail4, orderDetail5, orderDetail6));
        Order order3 = createOrder(member, address, "3", List.of(orderDetail7, orderDetail8, orderDetail9));
        Order order4 = createOrder(member, address, "4", List.of(orderDetail10, orderDetail11, orderDetail12));
        orderCommendRepository.saveAll(List.of(order1, order2, order3, order4));

        PageRequest pageSize1 = PageRequest.of(0, 1);
        PageRequest pageSize2 = PageRequest.of(0, 2);
        PageRequest pageSize2_1 = PageRequest.of(1, 2);

        GetOrderHttp.Condition condition
                = createCondition(member.getId(), null, String.valueOf(LocalDateTime.now().getYear()));

        //when
//        orderDtoRepository.findByMemberIdWithMemberAddress(condition, pageSize1);
//        orderDtoRepository.findByMemberIdWithMemberAddress(condition, pageSize2);
        Page<OrderDto> orders = orderDtoRepository.findByMemberIdWithMemberAddress(condition, pageSize2_1);

        //then
        assertThat(orders).hasSize(2);

    }

    private GetOrderHttp.Condition createCondition(Long memberId, String keyword, String year) {
        return GetOrderHttp.Condition.builder()
                .memberId(memberId)
                .keyword(keyword)
                .year(year)
                .build();
    }

    private Order createOrder(Member member1, Address address, String orderNo, List<OrderDetail> orderDetails) {

        Order order = Order.builder()
                .member(member1)
                .address(address)
                .orderNo(orderNo)
                .build();

        // order <-> orderDetail 연관관계
        orderDetails.forEach(order::addOrderDetail);

        return order;
    }

    private OrderDetail createOrderDetail(Product product,  Long quantity, String orderNo) {
        return OrderDetail.builder()
                .product(product)
                .quantity(quantity)
                .price(product.getPrice() * quantity)
                .orderNo(orderNo)
                .build();
    }

    private Product createProduct(Long price, String no) {
        return Product.builder()
                .price(price)
                .thumbImg("썸네일"+no)
                .name("상품"+no)
                .build();
    }

    private Member createMember(String no) {
        return Member.builder()
                .name("홍길동"+no)
                .build();
    }

}