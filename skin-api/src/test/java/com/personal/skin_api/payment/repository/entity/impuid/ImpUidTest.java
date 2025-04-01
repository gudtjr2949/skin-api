package com.personal.skin_api.payment.repository.entity.impuid;

import com.personal.skin_api.common.exception.RestApiException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ImpUidTest {

    @Test
    void imp_Uid에_Null이_입력될_경우_예외가_발생한다() {
        // given
        String nullUid = null;

        // when & then
        assertThatThrownBy(() -> new ImpUid(nullUid))
                .isInstanceOf(RestApiException.class);
    }

    @Test
    void imp_Uid에_빈_문자열이_입력될_경우_예외가_발생한다() {
        // given
        String emptyUid = "";

        // when & then
        assertThatThrownBy(() -> new ImpUid(emptyUid))
                .isInstanceOf(RestApiException.class);
    }

    @Test
    void imp_Uid에_imp로_시작하지_않을_경우_예외가_발생한다() {
        // given
        String invalidUid = "ii_123456789098";

        // when & then
        assertThatThrownBy(() -> new ImpUid(invalidUid))
                .isInstanceOf(RestApiException.class);
    }

    @Test
    void imp_Uid의_길이가_16이_아닐_경우_예외가_발생한다() {
        // given
        String invalidUid = "imp_12345";

        // when
        assertThatThrownBy(() -> new ImpUid(invalidUid))
                .isInstanceOf(RestApiException.class);
    }

}