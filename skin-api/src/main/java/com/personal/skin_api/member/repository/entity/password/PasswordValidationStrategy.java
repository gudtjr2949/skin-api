package com.personal.skin_api.member.repository.entity.password;

interface PasswordValidationStrategy {
    void validate(final String password);
}
