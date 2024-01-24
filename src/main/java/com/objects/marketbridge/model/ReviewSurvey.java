package com.objects.marketbridge.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewSurvey extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_survey_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_survey_category_id")
    private ReviewSurveyCategory reviewSurveyCategoryId;

    // 추가
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_content_id")
    private SurveyContent surveyContentId;

    @Builder
    private ReviewSurvey(Review reviewId, ReviewSurveyCategory reviewSurveyCategoryId ,SurveyContent surveyContentId) {
        this.reviewId = reviewId;
        this.reviewSurveyCategoryId = reviewSurveyCategoryId;
        this.surveyContentId = surveyContentId;
    }
}
