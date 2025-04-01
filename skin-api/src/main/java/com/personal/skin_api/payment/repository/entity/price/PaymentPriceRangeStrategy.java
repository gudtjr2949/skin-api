package com.personal.skin_api.payment.repository.entity.price;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.payment.PaymentPriceErrorCode;
import com.personal.skin_api.common.exception.product.PriceErrorCode;

public class PaymentPriceRangeStrategy implements PaymentPriceValidationStrategy {

    public static final Long MIN_PRICE = 0L, MAX_PRICE = 200_000_000L;

    /**
     * price에 최대값, 최소값 범위에 들어오는지 검증한다.
     * @param price 정상 범위 여부를 확인할 가격
     */
    @Override
    public void validate(final Long price) {
        if (price < 100 || price > MAX_PRICE) {
            throw new RestApiException(PaymentPriceErrorCode.IMPOSSIBLE_PRICE_RANGE);
        }
    }
}
