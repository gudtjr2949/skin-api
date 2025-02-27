package com.personal.skin_api.member.repository.entity.password;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.member.PasswordErrorCode;

class ModifyPasswordReuseStrategy implements ModifyPasswordValidationStrategy {

    @Override
    public void validate(String beforePassword, String newPassword) {
        if (beforePassword.equals(newPassword)) throw new RestApiException(PasswordErrorCode.PASSWORD_REUSE_FORBIDDEN);
    }
}
