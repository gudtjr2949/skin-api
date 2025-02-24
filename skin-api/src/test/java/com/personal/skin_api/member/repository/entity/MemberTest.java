package com.personal.skin_api.member.repository.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemberTest {

    @Test
    void 사용자를_빌더를_사용해_생성한다() {
        // given
        String password = "asd1234!";
        String memberName = "홍길동";
        String nickname = "길동짱짱";
        String phone = "01012345678";

        // when
        Member member = Member.builder()
                .password(password)
                .memberName(memberName)
                .nickname(nickname)
                .password(password)
                .phone(phone)
                .build();

        // then
        assertThat(member.getMemberName()).isEqualTo(memberName);
    }
}