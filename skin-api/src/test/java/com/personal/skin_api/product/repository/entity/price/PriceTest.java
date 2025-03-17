package com.personal.skin_api.product.repository.entity.price;

import com.personal.skin_api.common.exception.RestApiException;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class PriceTest {

    @Test
    void 가능한_금액_범위를_벗어난_경우_예외가_발생한다() {
        // given
        int tooLittlePrice = PriceRangeStrategy.MIN_PRICE - 1;
        int tooMuchPrice = PriceRangeStrategy.MAX_PRICE + 1;
        List<Integer> prices = List.of(tooLittlePrice, tooMuchPrice);

        // when & then
        prices.stream().forEach(price -> assertThatThrownBy(() -> new Price(price))
                .isInstanceOf(RestApiException.class));
    }

    @Test
    void 금액이_정상적으로_생성된다() {
        // given
        int normalPrice = 10_000;

        // when
        Price price = new Price(normalPrice);

        // then
        assertThat(price.getPrice()).isEqualTo(normalPrice);
    }

}