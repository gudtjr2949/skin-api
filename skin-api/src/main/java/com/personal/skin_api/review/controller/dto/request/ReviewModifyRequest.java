package com.personal.skin_api.review.controller.dto.request;

import com.personal.skin_api.review.service.dto.request.ReviewModifyServiceRequest;
import lombok.Getter;

@Getter
public class ReviewModifyRequest {
    private Long reviewId;
    private String newReviewContent;
    private int newStar;

    public ReviewModifyServiceRequest toService(final String email) {
        return ReviewModifyServiceRequest.builder()
                .email(email)
                .reviewId(reviewId)
                .newReviewContent(newReviewContent)
                .star(newStar)
                .build();
    }
}
