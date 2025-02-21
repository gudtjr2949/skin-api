package com.personal.skin_api.member.repository.entity.member_name;

import com.personal.skin_api.common.exception.RestApiException;

import static com.personal.skin_api.common.exception.MemberErrorCode.NAME_CANNOT_CONTAINS_SPACE;

class MemberNameSpaceStrategy implements MemberNameValidationStrategy {

    /**
     * 사용자 이름에 공백이 있는지 확인한다.
     * @param memberName 공백을 확인할 사용자 이름
     */
    @Override
    public void validate(String memberName) {
        if (memberName.contains(" "))
            throw new RestApiException(NAME_CANNOT_CONTAINS_SPACE);
    }
}
