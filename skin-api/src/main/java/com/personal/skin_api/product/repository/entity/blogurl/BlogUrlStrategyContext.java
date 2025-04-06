package com.personal.skin_api.product.repository.entity.blogurl;

import java.util.List;

public class BlogUrlStrategyContext {
    private static final List<BlogUrlValidationStrategy> blogUrlValidationStrategies = List.of(
            new BlogUrlNullStrategy()
    );

    static void runStrategy(final String blogUrl) {
        blogUrlValidationStrategies.stream().forEach(strategy -> strategy.validate(blogUrl));
    }
}