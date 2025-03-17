package com.personal.skin_api.product.repository.entity.product_name;

import com.personal.skin_api.common.exception.RestApiException;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ProductNameTest {
    
    @Test
    void 제품명에_null이_할당될_경우_예외가_발생한다() {
        // given
        String nullProductName = null;

        // when & then
        assertThatThrownBy(() -> new ProductName(nullProductName))
                .isInstanceOf(RestApiException.class);
    }

    @Test
    void 제품명의_길이가_범위를_벗어나는_경우_예외가_발생한다() {
        // given
        String tooShortProductName = "A";
        String tooLongProductName = "A".repeat(ProductNameLengthStrategy.PRODUCT_NAME_MAX_LENGTH +1);

        List<String> productNames = List.of(tooShortProductName, tooLongProductName);

        // when & then
        productNames.stream().forEach(productName -> assertThatThrownBy(() -> new ProductName(productName))
                .isInstanceOf(RestApiException.class));
    }
}