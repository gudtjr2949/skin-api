package com.personal.skin_api.member.repository.entity.password;

import com.personal.skin_api.common.exception.RestApiException;

import java.util.regex.Pattern;

import static com.personal.skin_api.common.exception.member.PasswordErrorCode.INVALID_PASSWORD_FORMAT;

class PasswordFormatStrategy implements PasswordValidationStrategy {
    private static final Pattern alphabetPattern = Pattern.compile("[a-zA-Z]"),
            numberPattern = Pattern.compile("\\d"),
            specialCharPattern = Pattern.compile("[!@?]");

    /**
     * 비밀번호에 알파벳, 숫자, 특수문자가 모두 포함되어 있는지 검증한다.
     * @param password 포맷을 검증할 비밀번호
     */
    @Override
    public void validate(final String password) {
        if (!(alphabetPattern.matcher(password).find()
                && numberPattern.matcher(password).find()
                && specialCharPattern.matcher(password).find()))
            throw new RestApiException(INVALID_PASSWORD_FORMAT);
    }
}
