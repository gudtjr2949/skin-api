package com.personal.skin_api.product.repository.entity.product_content;

import java.util.List;

public class ProductContentStrategyContext {

    private static final List<ProductContentValidationStrategy> strategies = List.of(
            new ProductContentNullStrategy(),
            new ProductContentLengthStrategy()
    );

    static void runStrategy(final String productContent) {
        strategies.stream().forEach(strategy -> strategy.validate(productContent));
    }
}
