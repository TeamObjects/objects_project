package com.objects.marketbridge.common.infra.entity;

import com.objects.marketbridge.common.domain.enums.ContentType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HelpDeskEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "help_desk_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntityId;
    //private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productId;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    private String content;

    @Builder
    private HelpDeskEntity(OrderEntity orderEntityId, MemberEntity memberId, ProductEntity productId, ContentType contentType, String content) {
        this.orderEntityId = orderEntityId;
        this.memberId = memberId;
        this.productId = productId;
        this.contentType = contentType;
        this.content = content;
    }
}
