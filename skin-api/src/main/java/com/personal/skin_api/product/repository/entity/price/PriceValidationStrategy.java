package com.personal.skin_api.product.repository.entity.price;

interface PriceValidationStrategy {
    void validate(final Long price);
}
