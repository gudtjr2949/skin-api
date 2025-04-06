package com.personal.skin_api.review.controller.dto.request;

import com.personal.skin_api.review.service.dto.request.ReviewModifyServiceRequest;

public class ReviewModifyRequest {
    private Long reviewId;
    private String newReviewContent;

    public ReviewModifyServiceRequest toService(final String email) {
        return ReviewModifyServiceRequest.builder()
                .email(email)
                .reviewId(reviewId)
                .newReviewContent(newReviewContent)
                .build();
    }
}
