package com.personal.skin_api.member.repository.entity.phone;

import com.personal.skin_api.common.exception.RestApiException;

import static com.personal.skin_api.common.exception.member.PasswordErrorCode.INVALID_PASSWORD_LENGTH;

class PhoneLengthStrategy implements PhoneValidationStrategy {
    private static final int PHONE_LENGTH = 11;

    /**
     * 전화번호 길이가 PHONE_LENGTH 와 일치하는지 검증한다.
     * @param phone 길이 검증할 전화번호
     */
    @Override
    public void validate(final String phone) {
        if (phone.length() != PHONE_LENGTH)
            throw new RestApiException(INVALID_PASSWORD_LENGTH);
    }
}
