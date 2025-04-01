package com.personal.skin_api.payment.repository.entity.price;

interface PaymentPriceValidationStrategy {
    void validate(final Long price);
}
