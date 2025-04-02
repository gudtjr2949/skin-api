package com.personal.skin_api.review.service.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

public class ReviewDetailResponse {
    private Long reviewId;
    private String reviewContent;
    private LocalDateTime createdAt;
    private String nickname;

    @Builder
    private ReviewDetailResponse(final Long reviewId, final String reviewContent,
                                 final LocalDateTime createdAt, final String nickname) {
        this.reviewId = reviewId;
        this.reviewContent = reviewContent;
        this.createdAt = createdAt;
        this.nickname = nickname;
    }
}
