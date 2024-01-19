package com.objects.marketbridge.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private Boolean isSubs;

    private Long quantity;

    @Builder

    public Cart(Member memberId, Product product, Boolean isSubs, Long quantity) {
        this.memberId = memberId;
        this.product = product;
        this.isSubs = isSubs;
        this.quantity = quantity;
    }

    //    private Cart(Product product, Member memberId, boolean isSubs, Long quantity) {
//        this.product = product;
//        this.memberId = memberId;
//        this.isSubs = isSubs;
//        this.quantity = quantity;
//    }
}