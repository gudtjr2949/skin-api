package com.personal.skin_api.review.service.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ReviewListResponse {
    private List<ReviewDetailResponse> reviewDetailResponses;

    public ReviewListResponse(final List<ReviewDetailResponse> reviewDetailResponses) {
        this.reviewDetailResponses = reviewDetailResponses;
    }
}
