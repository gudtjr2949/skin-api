package com.personal.skin_api.product.repository.entity.product_content;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.product.ProductContentErrorCode;

public class ProductContentNullStrategy implements ProductContentValidationStrategy {

    @Override
    public void validate(final String productContent) {
        if (productContent == null) {
            throw new RestApiException(ProductContentErrorCode.PRODUCT_CONTENT_CAN_NOT_BE_NULL);
        }
    }
}
