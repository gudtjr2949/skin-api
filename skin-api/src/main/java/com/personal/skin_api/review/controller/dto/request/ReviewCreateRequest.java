package com.personal.skin_api.review.controller.dto.request;

import com.personal.skin_api.review.service.dto.request.ReviewCreateServiceRequest;
import lombok.Getter;

@Getter
public class ReviewCreateRequest {
    private String orderUid;
    private String reviewContent;
    private int star;

    public ReviewCreateServiceRequest toService(final String email) {
        return ReviewCreateServiceRequest.builder()
                .email(email)
                .orderUid(orderUid)
                .reviewContent(reviewContent)
                .star(star)
                .build();
    }
}
