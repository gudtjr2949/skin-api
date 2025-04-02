package com.personal.skin_api.review.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewFindListServiceRequest {
    private Long productId;
    private Long reviewId;

    @Builder
    private ReviewFindListServiceRequest(final Long productId, final Long reviewId) {
        this.productId = productId;
        this.reviewId = reviewId;
    }
}
