package com.objects.marketbridge.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    //private Long orderId; orderid 제거

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product productId;

    private String content;
    // 별점
    private Integer rating; //1-5

    @Builder
    private Review(Member memberId, Product productId, String content, Integer rating) {
        this.memberId = memberId;
        this.productId = productId;
        this.content = content;
        this.rating = rating;
    }
}
