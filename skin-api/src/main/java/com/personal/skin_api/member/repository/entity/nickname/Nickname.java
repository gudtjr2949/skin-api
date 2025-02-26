package com.personal.skin_api.member.repository.entity.nickname;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.util.List;

@Embeddable
@NoArgsConstructor
public class Nickname {

    @Column(name = "NICKNAME")
    private String nickname;

    public Nickname(final String nickname) {
        validate(nickname);
        this.nickname = nickname;
    }

    private void validate(final String nickname) {
        NicknameStrategyContext.runStrategy(nickname);
    }

    public String getNickname() {
        return nickname;
    }
}