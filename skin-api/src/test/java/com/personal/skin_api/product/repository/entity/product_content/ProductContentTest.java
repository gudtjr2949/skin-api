package com.personal.skin_api.product.repository.entity.product_content;

import com.personal.skin_api.common.exception.RestApiException;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ProductContentTest {

    @Test
    void 제품내용에_Null이_입력될_경우_예외가_발생한다() {
        // given
        String nullProductContent = null;

        // when & then
        assertThatThrownBy(() -> new ProductContent(nullProductContent))
                .isInstanceOf(RestApiException.class);
    }

    @Test
    void 제품내용의_길이가_범위를_벗어나는_경우_예외가_발생한다() {
        // given
        String tooShortProductContent = "".repeat(ProductContentLengthStrategy.PRODUCT_CONTENT_MIN_LENGTH-1);
        String tooLongProductContent = "A".repeat(ProductContentLengthStrategy.PRODUCT_CONTENT_MAX_LENGTH+1);

        List<String> productContents = List.of(tooShortProductContent, tooLongProductContent);

        // when & then
        productContents.stream().forEach(productContent -> assertThatThrownBy(() -> new ProductContent(productContent))
                .isInstanceOf(RestApiException.class));
    }
    
    @Test
    void 제품내용이_정상적으로_생성된다() {
        // given
        String normalProductContent = "제품내용";

        // when
        ProductContent productContent = new ProductContent(normalProductContent);

        // then
        assertThat(productContent.getProductContent()).isEqualTo(normalProductContent);
    }
}