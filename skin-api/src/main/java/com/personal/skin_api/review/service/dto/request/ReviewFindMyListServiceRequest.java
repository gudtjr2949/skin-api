package com.personal.skin_api.review.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewFindMyListServiceRequest {

    private Long reviewId;
    private String email;

    @Builder
    private ReviewFindMyListServiceRequest(final Long reviewId, final String email) {
        this.reviewId = reviewId;
        this.email = email;
    }
}
