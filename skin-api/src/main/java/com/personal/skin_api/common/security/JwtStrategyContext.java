package com.personal.skin_api.common.security;

import java.util.List;

public class JwtStrategyContext {

    private static final List<JwtValidationStrategy> jwtValidationStrategies = List.of(
            new JwtNullStrategy()
    );

    static void runStrategy(final String token) {
        jwtValidationStrategies.stream().forEach(strategy -> strategy.validate(token));
    }
}
