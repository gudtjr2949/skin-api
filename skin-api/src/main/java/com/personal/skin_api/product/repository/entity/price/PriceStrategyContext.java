package com.personal.skin_api.product.repository.entity.price;

import java.util.List;

public class PriceStrategyContext {

    private static final List<PriceValidationStrategy> priceValidationStrategies = List.of(
            new PriceRangeStrategy()
    );

    static void runStrategy(final Long price) {
        priceValidationStrategies.stream().forEach(strategy -> strategy.validate(price));
    }
}
