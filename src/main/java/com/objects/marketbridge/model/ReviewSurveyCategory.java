package com.objects.marketbridge.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewSurveyCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_survey_category_id")
    private Long id;

    // TODO
    private Long productId;

    private String name;

    @Builder
    private ReviewSurveyCategory(Long productId, String name) {
        this.productId = productId;
        this.name = name;
    }
}
