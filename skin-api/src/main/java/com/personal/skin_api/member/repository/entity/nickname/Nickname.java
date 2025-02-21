package com.personal.skin_api.member.repository.entity.nickname;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.util.List;

@Embeddable
@NoArgsConstructor
public class Nickname {
    private static final List<NicknameValidationStrategy> nicknameValidationStrategies = List.of(
            new NicknameNullStrategy(),
            new NicknameBlankStrategy(),
            new NicknameLengthStrategy(),
            new NicknameFormatStrategy()
    );

    @Column(name = "NICKNAME")
    private String nickname;

    public Nickname(final String nickname) {
        validate(nickname);
        this.nickname = nickname;
    }

    private void validate(String nickname) {
        nicknameValidationStrategies.stream().forEach(strategy -> strategy.validate(nickname));
    }

    public String getNickname() {
        return nickname;
    }
}