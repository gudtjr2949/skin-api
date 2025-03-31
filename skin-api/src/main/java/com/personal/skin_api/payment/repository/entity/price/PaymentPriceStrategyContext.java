package com.personal.skin_api.payment.repository.entity.price;

import java.util.List;

public class PaymentPriceStrategyContext {

    private static final List<PaymentPriceRangeStrategy> paymentPriceValidationStrategies = List.of(
            new PaymentPriceRangeStrategy()
    );

    static void runStrategy(final Long price) {
        paymentPriceValidationStrategies.stream().forEach(strategy -> strategy.validate(price));
    }
}
