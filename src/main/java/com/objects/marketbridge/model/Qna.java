package com.objects.marketbridge.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Qna extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId; //memberId

    // @AttributeOverride()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller sellerId; //memberId

    @Enumerated(EnumType.STRING)
    private ContentType boardType;

    private String content;

    @Builder
    private Qna(Member memberId, Seller sellerId, ContentType boardType, String content) {
        this.memberId = memberId;
        this.sellerId = sellerId;
        this.boardType = boardType;
        this.content = content;
    }
}
