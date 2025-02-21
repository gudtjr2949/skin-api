package com.personal.skin_api.member.repository.entity;

import com.personal.skin_api.common.exception.RestApiException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;


@ActiveProfiles("test")
class MemberNameTest {

    @Test
    void 이름이_NULL인_경우_예외가_발생한다() {
        // given
        String nullName = null;

        // when & then
        assertThatThrownBy(() -> new MemberName(nullName)).isInstanceOf(RestApiException.class);
    }
}