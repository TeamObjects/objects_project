package com.objects.marketbridge.domain;

import jakarta.persistence.*;
import jakarta.validation.OverridesAttribute;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Qna extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    // TODO
    // @AttributeOverride()  : 중복 클래스 적용
    private Long userCustomerId; //userId
    // TODO
    // @AttributeOverride()
    private Long userSellerId; //userId

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    private String content;

    @Builder
    private Qna(Long userCustomerId, Long userSellerId, ContentType contentType, String content) {
        this.userCustomerId = userCustomerId;
        this.userSellerId = userSellerId;
        this.contentType = contentType;
        this.content = content;
    }

}
