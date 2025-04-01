package com.personal.skin_api.review.repository.entity.review_content;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class ReviewContent {

    @Column(name = "REVIEW_CONTENT")
    private String reviewContent;

    public ReviewContent(final String reviewContent) {
        validate(reviewContent);
        this.reviewContent = reviewContent;
    }

    private void validate(final String reviewContent) {
        ReviewContentStrategyContext.runStrategy(reviewContent);
    }

    public String getReviewContent() {
        return reviewContent;
    }
}