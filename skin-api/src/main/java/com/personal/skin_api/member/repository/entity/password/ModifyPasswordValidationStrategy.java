package com.personal.skin_api.member.repository.entity.password;

interface ModifyPasswordValidationStrategy {
    void validate(final String beforePassword, final String newPassword);
}
