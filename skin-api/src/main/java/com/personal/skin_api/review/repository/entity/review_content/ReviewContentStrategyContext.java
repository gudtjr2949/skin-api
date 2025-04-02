package com.personal.skin_api.review.repository.entity.review_content;


import java.util.List;

public class ReviewContentStrategyContext {

    private static final List<ReviewContentValidationStrategy> reviewContentValidationStrategies = List.of(
            new ReviewContentNullStrategy(),
            new ReviewContentEmptyStrategy(),
            new ReviewContentLengthStrategy()
    );

    static void runStrategy(final String reviewContent) {
        reviewContentValidationStrategies.stream().forEach(strategy -> strategy.validate(reviewContent));
    }
}
