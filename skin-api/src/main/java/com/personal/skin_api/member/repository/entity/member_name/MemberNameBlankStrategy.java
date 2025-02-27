package com.personal.skin_api.member.repository.entity.member_name;

import com.personal.skin_api.common.exception.RestApiException;

import static com.personal.skin_api.common.exception.member.MemberNameErrorCode.NAME_CANNOT_CONTAINS_BLANK;

class MemberNameBlankStrategy implements MemberNameValidationStrategy {

    /**
     * 사용자 이름에 공백이 있는지 검증한다.
     * @param memberName 공백 포함 여부를 검증할 사용자 이름
     */
    @Override
    public void validate(final String memberName) {
        if (memberName.contains(" "))
            throw new RestApiException(NAME_CANNOT_CONTAINS_BLANK);
    }
}
