package com.personal.skin_api.product.repository.entity.product_name;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.product.ProductNameErrorCode;

public class ProductNameNullStrategy implements ProductNameValidationStrategy {

    @Override
    public void validate(final String productName) {
        if (productName == null) {
            throw new RestApiException(ProductNameErrorCode.PRODUCT_NAME_CAN_NOT_BE_NULL);
        }
    }
}
