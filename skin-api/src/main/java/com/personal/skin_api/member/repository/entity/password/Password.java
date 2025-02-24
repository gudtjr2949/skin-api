package com.personal.skin_api.member.repository.entity.password;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.util.List;

// TODO : 일반 문자열 형태의 비밀번호 암호화 & 복호화 기능 추가 필요
@Embeddable
@NoArgsConstructor
public class Password {
    private static final List<PasswordValidationStrategy> passwordValidationStrategies = List.of(
            new PasswordNullStrategy(),
            new PasswordBlankStrategy(),
            new PasswordLengthStrategy(),
            new PasswordFormatStrategy()
    );

    private static final List<ModifyPasswordValidationStrategy> modifyPasswordValidationStrategies = List.of(
            new ModifyPasswordReuseStrategy()
    );

    @Column(name = "PASSWORD")
    private String password;

     public Password(final String password) {
        validate(password);
        this.password = password;
    }

    private void validate(String password) {
        passwordValidationStrategies.stream().forEach(strategy -> strategy.validate(password));
    }

    public String getPassword() {
        return password;
    }

    Password modifyPassword(final String newPassword) {
        validateNewPassword(newPassword);
        return new Password(newPassword);
    }

    private void validateNewPassword(String newPassword) {
        modifyPasswordValidationStrategies.stream().forEach(strategy -> strategy.validate(this.password, newPassword));
    }
}
