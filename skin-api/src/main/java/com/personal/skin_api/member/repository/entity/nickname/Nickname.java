package com.personal.skin_api.member.repository.entity.nickname;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
class Nickname {

    @Column(name = "NICKNAME")
    private String nickname;

    public Nickname(final String nickname) {

        this.nickname = nickname;
    }
}