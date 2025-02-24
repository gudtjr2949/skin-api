package com.personal.skin_api.member.repository.entity.email;

import com.personal.skin_api.common.exception.RestApiException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class EmailTest {
    
    @Test
    void 이메일이_NULL일_경우_예외가_발생한다() {
        // given
        String nullEmail = null;

        // when & then
        assertThatThrownBy(() -> new Email(nullEmail)).isInstanceOf(RestApiException.class);
    }
}