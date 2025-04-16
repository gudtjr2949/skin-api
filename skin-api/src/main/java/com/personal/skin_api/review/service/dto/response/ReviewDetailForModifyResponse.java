package com.personal.skin_api.review.service.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewDetailForModifyResponse {
    private Long reviewId;
    private String reviewContent;
    private int star;

    @Builder
    private ReviewDetailForModifyResponse(final Long reviewId,
                                          final String reviewContent,
                                          final int star) {
        this.reviewId = reviewId;
        this.reviewContent = reviewContent;
        this.star = star;
    }
}
