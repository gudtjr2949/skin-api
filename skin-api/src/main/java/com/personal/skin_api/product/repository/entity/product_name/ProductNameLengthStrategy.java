package com.personal.skin_api.product.repository.entity.product_name;

import com.personal.skin_api.common.exception.RestApiException;

import com.personal.skin_api.common.exception.product.ProductNameErrorCode;

public class ProductNameLengthStrategy implements ProductNameValidationStrategy {

    public static final int PRODUCT_NAME_MIN_LENGTH = 2, PRODUCT_NAME_MAX_LENGTH = 50;

    @Override
    public void validate(final String productName) {
        if (productName.length() < PRODUCT_NAME_MIN_LENGTH || productName.length() > PRODUCT_NAME_MAX_LENGTH) {
            throw new RestApiException(ProductNameErrorCode.INVALID_PRODUCT_NAME_LENGTH);
        }
    }
}
