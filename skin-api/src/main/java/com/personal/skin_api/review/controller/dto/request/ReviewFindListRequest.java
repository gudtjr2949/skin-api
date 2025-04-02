package com.personal.skin_api.review.controller.dto.request;

import com.personal.skin_api.review.service.dto.request.ReviewFindListServiceRequest;

public class ReviewFindListRequest {
    private Long productId;
    private Long reviewId;

    public ReviewFindListServiceRequest toService() {
        return ReviewFindListServiceRequest.builder()
                .productId(productId)
                .reviewId(reviewId)
                .build();
    }
}
