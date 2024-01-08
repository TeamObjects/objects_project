package com.objects.marketbridge.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProdTag extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "prog_tag_id")
    private Long id;
    // TODO
    private Long tagId;
    // TODO
    private Long productId;

    @Builder
    private ProdTag(Long tagId, Long productId) {
        this.tagId = tagId;
        this.productId = productId;
    }
}
