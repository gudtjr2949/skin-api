package com.personal.skin_api.payment.repository.entity.impuid;

import com.personal.skin_api.payment.repository.entity.price.PaymentPriceRangeStrategy;

import java.util.List;

public class ImpUidStrategyContext {

    private static final List<ImpUidValidationStrategy> impUidValidationStrategies = List.of(
            new ImpUidNullStrategy(),
            new ImpUidFormatStrategy(),
            new ImpUidLengthStrategy()
    );

    static void runStrategy(final String impUid) {
        impUidValidationStrategies.stream().forEach(strategy -> strategy.validate(impUid));
    }
}
