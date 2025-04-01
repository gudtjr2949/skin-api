package com.personal.skin_api.payment.repository.entity.price;

import com.personal.skin_api.common.exception.RestApiException;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static com.personal.skin_api.payment.repository.entity.price.PaymentPriceRangeStrategy.*;
import static org.assertj.core.api.Assertions.*;

class PaymentPriceTest {
    @Test
    void 제품_금액이_기준금액보다_낮은_경우_예외가_발생한다() {
        // given
        Long lowPrice = MIN_PRICE-1;

        // when & then
        assertThatThrownBy(() -> new PaymentPrice(lowPrice)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 제품_금액이_기준금액보다_높은_경우_예외가_발생한다() {
        // given
        Long highPrice = MAX_PRICE+1;

        // when & then
        assertThatThrownBy(() -> new PaymentPrice(highPrice)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 제품_금액이_정상범위에_포함될_경우_객체가_생성된다() {
        // given
        Long normalPrice = new Random().nextLong(MAX_PRICE - MIN_PRICE + 1) + MIN_PRICE;

        // when
        PaymentPrice paymentPrice = new PaymentPrice(normalPrice);

        // then
        assertThat(paymentPrice).isNotNull();
        assertThat(paymentPrice.getPrice()).isEqualTo(normalPrice);
    }
}