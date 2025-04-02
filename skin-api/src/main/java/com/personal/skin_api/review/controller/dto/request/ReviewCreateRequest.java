package com.personal.skin_api.review.controller.dto.request;

import com.personal.skin_api.review.service.dto.request.ReviewCreateServiceRequest;

public class ReviewCreateRequest {
    private String orderUid;
    private String reviewContent;

    public ReviewCreateServiceRequest toService(final String email) {
        return ReviewCreateServiceRequest.builder()
                .email(email)
                .orderUid(orderUid)
                .reviewContent(reviewContent)
                .build();
    }
}
