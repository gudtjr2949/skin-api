package com.personal.skin_api.review.repository.entity.review_content;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.review.ReviewContentErrorCode;

public class ReviewContentNullStrategy implements ReviewContentValidationStrategy {

    @Override
    public void validate(final String reviewContent) {
        if (reviewContent == null)
            throw new RestApiException(ReviewContentErrorCode.REVIEW_CONTENT_CAN_NOT_BE_NULL);
    }
}
