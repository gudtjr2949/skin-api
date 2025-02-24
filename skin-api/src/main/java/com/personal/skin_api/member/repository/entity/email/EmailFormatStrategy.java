package com.personal.skin_api.member.repository.entity.email;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.member.EmailErrorCode;

import java.util.regex.Pattern;

class EmailFormatStrategy implements EmailValidationStrategy {

    private static final Pattern pattern = Pattern.compile( "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");


    /**
     * 이메일이 올바른 형식인지 검증한다.
     * @param email 포맷을 검증할 이메일
     */
    @Override
    public void validate(final String email) {
        if (!pattern.matcher(email).find()) throw new RestApiException(EmailErrorCode.INVALID_EMAIL_FORMAT);
    }
}
