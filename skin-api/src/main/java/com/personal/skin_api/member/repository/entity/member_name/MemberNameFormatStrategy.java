package com.personal.skin_api.member.repository.entity.member_name;

import com.personal.skin_api.common.exception.RestApiException;

import java.util.regex.Pattern;

import static com.personal.skin_api.common.exception.member.MemberNameErrorCode.INVALID_NAME_FORMAT;

class MemberNameFormatStrategy implements MemberNameValidationStrategy {

    private static final Pattern numberPattern = Pattern.compile("\\d"),
            specialCharPattern = Pattern.compile("[!@?]");

    /**
     * 사용자 이름에 알파벳과 한글만 포함되어 있는지 검증한다.
     * @param memberName 포맷을 검증할 사용자 이름
     */
    @Override
    public void validate(final String memberName) {
        if (numberPattern.matcher(memberName).find() || specialCharPattern.matcher(memberName).find())
            throw new RestApiException(INVALID_NAME_FORMAT);
    }
}
