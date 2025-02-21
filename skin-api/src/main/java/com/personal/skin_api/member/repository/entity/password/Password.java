package com.personal.skin_api.member.repository.entity.password;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.util.List;

@Embeddable
@NoArgsConstructor
public class Password {
    private static final List<PasswordValidationStrategy> passwordValidationStrategies = List.of(
            new PasswordNullStrategy(),
            new PasswordBlankStrategy(),
            new PasswordLengthStrategy(),
            new PasswordFormatStrategy()
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
}
