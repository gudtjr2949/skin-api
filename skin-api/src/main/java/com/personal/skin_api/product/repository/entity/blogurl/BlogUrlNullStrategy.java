package com.personal.skin_api.product.repository.entity.blogurl;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.product.BlogUrlErrorCode;

public class BlogUrlNullStrategy implements BlogUrlValidationStrategy {

    @Override
    public void validate(final String blogUrl) {
        if (blogUrl == null)
            throw new RestApiException(BlogUrlErrorCode.BLOG_URL_CAN_NOT_BE_NULL);
    }
}
