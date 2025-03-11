package com.personal.skin_api.product.repository.entity.product_name;

import com.personal.skin_api.common.exception.RestApiException;

import com.personal.skin_api.common.exception.product.ProductNameErrorCode;

public class ProductNameLengthStrategy implements ProductNameValidationStrategy {

    public static final int MIN_LENGTH = 2, MAX_LENGTH = 50;

    @Override
    public void validate(final String productName) {
        if (productName.length() < MIN_LENGTH || productName.length() > MAX_LENGTH) {
            throw new RestApiException(ProductNameErrorCode.INVALID_PRODUCT_NAME_LENGTH);
        }
    }
}
