package com.personal.skin_api.product.repository.entity.product_content;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.product.ProductContentErrorCode;

public class ProductContentLengthStrategy implements ProductContentValidationStrategy {

    public static final int PRODUCT_CONTENT_MIN_LENGTH = 1, PRODUCT_CONTENT_MAX_LENGTH = 1_000;
    
    @Override
    public void validate(final String productContent) {
        if (productContent.length() < PRODUCT_CONTENT_MIN_LENGTH || productContent.length() > PRODUCT_CONTENT_MAX_LENGTH) {
            throw new RestApiException(ProductContentErrorCode.INVALID_PRODUCT_CONTENT_LENGTH);
        }
    }
}
