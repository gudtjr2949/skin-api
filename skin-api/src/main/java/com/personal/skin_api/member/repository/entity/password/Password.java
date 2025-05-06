package com.personal.skin_api.member.repository.entity.password;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

// TODO : 일반 문자열 형태의 비밀번호 암호화 & 복호화 기능 추가 필요
@Embeddable
@NoArgsConstructor
public class Password {

    @Column(name = "PASSWORD")
    private String password;

     public Password(final String password) {
        this.password = password;
    }

    public static Password fromPlain(final String rawPassword) {
        validate(rawPassword);
        return new Password(rawPassword);
    }

    public static Password fromEncoded(final String encodedPassword) {
        return new Password(encodedPassword);
    }

    private static void validate(final String password) {
         PasswordStrategyContext.runStrategy(password);
    }

    public String getPassword() {
        return password;
    }

    public Password setEncodedPassword(final String encodedPassword) {
         this.password = encodedPassword;
         return this;
    }

    public Password modifyPassword(final String newPassword) {
        validateNewPassword(newPassword);
        return new Password(newPassword);
    }

    private void validateNewPassword(final String newPassword) {
        ModifyPasswordStrategyContext.runStrategy(this.password, newPassword);
    }
}
