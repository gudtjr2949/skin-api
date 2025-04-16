package com.personal.skin_api.review.service.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewDetailResponse {
    private Long reviewId;
    private String productName;
    private String reviewContent;
    private LocalDateTime createdAt;
    private String nickname;
    private int star;

    @Builder
    private ReviewDetailResponse(final Long reviewId,
                                 final String productName,
                                 final String reviewContent,
                                 final LocalDateTime createdAt,
                                 final String nickname,
                                 final int star) {
        this.reviewId = reviewId;
        this.productName = productName;
        this.reviewContent = reviewContent;
        this.createdAt = createdAt;
        this.nickname = nickname;
        this.star = star;
    }
}
