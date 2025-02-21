package com.personal.skin_api.member.repository.entity;

import com.personal.skin_api.common.exception.RestApiException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

import static com.personal.skin_api.common.exception.MemberErrorCode.*;

@Embeddable
@NoArgsConstructor
public class Password {
    private static final int PASSWORD_MIN_LENGTH = 8, PASSWORD_MAX_LENGTH = 20;
    private static final Pattern alphabetPattern = Pattern.compile("[a-zA-Z]"),
            numberPattern = Pattern.compile("\\d"),
            specialCharPattern = Pattern.compile("[!@?]");

    @Column(name = "PASSWORD")
    private String password;

    public Password(final String password) {
        validate(password);
        this.password = password;
    }

    private void validate(String password) {
        validatePasswordNull(password);
        validatePasswordLength(password);
        validatePasswordComplexity(password);
    }

    /**
     * 비밀번호는 null 일 수 없다.
     * @param password : null 을 검증할 비밀번호
     */
    private void validatePasswordNull(String password) {
        if (password == null)
            throw new RestApiException(PASSWORD_CANNOT_BE_NULL);
    }

    /**
     * 비밀번호의 길이는 PASSWORD_MIN_LENGTH 이상 PASSWORD_MAX_LENGTH 이하여야 한다.
     * @param password : 길이 검증할 비밀번호
     */
    private void validatePasswordLength(String password) {
        if (password.isBlank() || password.length() < PASSWORD_MIN_LENGTH || password.length() > PASSWORD_MAX_LENGTH)
            throw new RestApiException(INVALID_PASSWORD_LENGTH);
    }

    /**
     * 비밀번호에 알파벳, 숫자, 특수문자가 모두 포함되어야 한다.
     * @param password : 복잡도를 검증할 비밀번호
     */
    private void validatePasswordComplexity(String password) {
        if (!(alphabetPattern.matcher(password).find()
                && numberPattern.matcher(password).find()
                && specialCharPattern.matcher(password).find()))
            throw new RestApiException(INVALID_PASSWORD_COMPLEXITY);
    }

    public String getPassword() {
        return password;
    }
}
