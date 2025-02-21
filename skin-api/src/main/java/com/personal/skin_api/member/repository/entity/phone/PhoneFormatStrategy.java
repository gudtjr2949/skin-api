package com.personal.skin_api.member.repository.entity.phone;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.member.PhoneErrorCode;

import java.util.regex.Pattern;

import static com.personal.skin_api.common.exception.member.PhoneErrorCode.*;

class PhoneFormatStrategy implements PhoneValidationStrategy {
    private static final Pattern alphabetPattern = Pattern.compile("[a-zA-Z]"),
            specialCharPattern = Pattern.compile("[!@?]");
    private static final String PREFIX = "010";

    /**
     * 전화번호의 시작부분이 010이고, 알파벳이나 특수문자가 포함되어 있는지 검증한다.
     * @param phone 포맷을 검증할 전화번호
     */
    @Override
    public void validate(final String phone) {
        if (!phone.startsWith(PREFIX) || alphabetPattern.matcher(phone).find() || specialCharPattern.matcher(phone).find())
            throw new RestApiException(INVALID_PHONE_FORMAT);
    }
}
