package com.personal.skin_api.product.repository.entity.product_name;

import java.util.List;

public class ProductNameStrategyContext {
    private static final List<ProductNameValidationStrategy> strategies = List.of(
            new ProductNameNullStrategy(),
            new ProductNameLengthStrategy()
    );

    static void runStrategy(final String productName) {
        strategies.stream().forEach(strategy -> strategy.validate(productName));
    }
}
