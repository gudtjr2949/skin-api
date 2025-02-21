package com.personal.skin_api.member.repository.entity.member_name;

import com.personal.skin_api.common.exception.RestApiException;

import static com.personal.skin_api.common.exception.MemberErrorCode.INVALID_NAME_LENGTH;

class MemberNameLengthStrategy implements MemberNameValidationStrategy {
    private static final int NAME_MIN_LENGTH = 8, NAME_MAX_LENGTH = 20;

    /**
     * 사용자 이름 길이는 NAME_MIN_LENGTH 이상, NAME_MAX_LENGTH 이하여야 한다.
     * @param memberName 길이 검증할 사용자 이름
     */
    @Override
    public void validate(final String memberName) {
        if (memberName.length() < NAME_MIN_LENGTH || memberName.length() > NAME_MAX_LENGTH)
            throw new RestApiException(INVALID_NAME_LENGTH);
    }
}
