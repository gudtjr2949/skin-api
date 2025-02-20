package com.personal.skin_api.member.repository.entity;

import com.personal.skin_api.common.exception.MemberErrorCode;
import com.personal.skin_api.common.exception.RestApiException;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import static com.personal.skin_api.common.exception.MemberErrorCode.*;

@Embeddable
@NoArgsConstructor
public class Password {
    private final int PASSWORD_MIN_LENGTH = 8, PASSWORD_MAX_LENGTH = 20;
    private String password;

    private Password(final String password) {
        validatePasswordLength(password);

        this.password = password;
    }

    /**
     *
     * @param password : 길이 검증할 비밀번호
     */
    private void validatePasswordLength(String password) {
        if (password.length() < PASSWORD_MIN_LENGTH || password.length() > PASSWORD_MAX_LENGTH)
            throw new RestApiException(INVALID_PASSWORD_LENGTH);
    }

    /**
     * 비밀번호에 알파벳, 숫자, 특수문자가 모두 포함되어야 한다.
     * @param password : 복잡도를 검증할 비밀번호
     */
    private void validatePasswordComplexity(String password) {

    }

}
