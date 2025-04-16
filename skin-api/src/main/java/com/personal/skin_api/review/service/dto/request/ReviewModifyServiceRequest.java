package com.personal.skin_api.review.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewModifyServiceRequest {
    private Long reviewId;
    private String newReviewContent;
    private String email;
    private int star;

    @Builder
    private ReviewModifyServiceRequest(final Long reviewId,
                                       final String newReviewContent,
                                       final String email,
                                       final int star) {
        this.reviewId = reviewId;
        this.newReviewContent = newReviewContent;
        this.email = email;
        this.star = star;
    }
}
