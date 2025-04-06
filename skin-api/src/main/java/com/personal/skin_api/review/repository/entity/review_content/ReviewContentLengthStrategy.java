package com.personal.skin_api.review.repository.entity.review_content;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.review.ReviewContentErrorCode;

public class ReviewContentLengthStrategy implements ReviewContentValidationStrategy {

    public static final int REVIEW_CONTENT_MIN_LENGTH = 10;
    public static final int REVIEW_CONTENT_MAX_LENGTH = 100;

    @Override
    public void validate(final String reviewContent) {
        if (reviewContent.replaceAll(" ", "").length() < REVIEW_CONTENT_MIN_LENGTH
        || reviewContent.replaceAll(" ", "").length() >= REVIEW_CONTENT_MAX_LENGTH)
            throw new RestApiException(ReviewContentErrorCode.INVALID_REVIEW_CONTENT_LENGTH);
    }
}
