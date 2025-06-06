package com.personal.skin_api.member.service.dto.request;

import com.personal.skin_api.member.repository.entity.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.personal.skin_api.member.repository.entity.MemberRole.GENERAL;
import static com.personal.skin_api.member.repository.entity.MemberStatus.ACTIVE;

@Getter
public class MemberSignUpServiceRequest {
    private final String email;
    private final String password;
    private final String memberName;
    private final String nickname;
    private final String phone;

    @Builder
    private MemberSignUpServiceRequest(final String email,
                                       final String password,
                                       final String memberName,
                                       final String nickname,
                                       final String phone) {
        this.email = email;
        this.password = password;
        this.memberName = memberName;
        this.nickname = nickname;
        this.phone = phone;
    }

    public Member toEntity(final String encodedPassword) {
        return Member.builder()
                .email(email)
                .password(encodedPassword)
                .memberName(memberName)
                .nickname(nickname)
                .phone(phone)
                .status(ACTIVE)
                .role(GENERAL)
                .provider("LOCAL")
                .build();
    }
}
