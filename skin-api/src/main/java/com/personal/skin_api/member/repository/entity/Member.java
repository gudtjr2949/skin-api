package com.personal.skin_api.member.repository.entity;

import com.personal.skin_api.common.entity.BaseEntity;

import com.personal.skin_api.member.repository.entity.email.Email;
import com.personal.skin_api.member.repository.entity.member_name.MemberName;
import com.personal.skin_api.member.repository.entity.nickname.Nickname;
import com.personal.skin_api.member.repository.entity.password.Password;
import com.personal.skin_api.member.repository.entity.phone.Phone;
import com.personal.skin_api.member.service.dto.request.MemberModifyDetailServiceRequest;
import com.personal.skin_api.member.service.dto.request.MemberSignUpServiceRequest;
import jakarta.persistence.*;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.personal.skin_api.member.repository.entity.AuthProvider.*;
import static com.personal.skin_api.member.repository.entity.MemberRole.*;
import static com.personal.skin_api.member.repository.entity.MemberStatus.*;

@Entity
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Embedded
    private MemberName memberName;

    @Embedded
    private Nickname nickname;

    @Embedded
    private Phone phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "STATUS")
    private MemberStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "ROLE")
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "AUTH")
    private AuthProvider authProvider;

    @Builder
    private Member(final String email,
                   final String password,
                   final String memberName,
                   final String nickname,
                   final String phone,
                   final MemberStatus status,
                   final MemberRole role,
                   final String provider) {
        this.email = new Email(email);
        this.password = Password.fromEncoded(password);
        this.memberName = new MemberName(memberName);
        this.nickname = new Nickname(nickname);
        this.phone = new Phone(phone);
        this.status = status;
        this.role = role;
        this.authProvider = AuthProvider.toAuthProvider(provider);
    }

    public static Member fromOAuth(final String email,
                                   final String memberName,
                                   final String phone,
                                   final String provider) {
        return Member.builder()
                .email(email)
                .password(Password.fromOAuth())
                .memberName(memberName)
                .nickname(Nickname.OAUTH_NOT_SET)
                .phone(phone)
                .status(ACTIVE)
                .role(GENERAL)
                .provider(provider)
                .status(ACTIVE)
                .role(GENERAL)
                .build();
    }

    public void modifyPassword(final String newPassword) {
        this.password = Password.fromEncoded(newPassword);
    }

    public void modifyMemberInfo(final MemberModifyDetailServiceRequest request) {
        this.memberName = new MemberName(request.getNewMemberName());
        this.nickname = new Nickname(request.getNewNickname());
        this.phone = new Phone(request.getNewPhone());
    }

    public void modifyNickname(final String nickname) {
        this.nickname = new Nickname(nickname);
    }

    public void withdraw() {
        this.status = WITHDRAWN;
    }

    public Long getId() {
        return id;
    }

    public String getMemberName() {
        return memberName.getMemberName();
    }

    public String getEmail() {
        return email.getEmail();
    }

    public String getPassword() {
        return password.getPassword();
    }

    public String getNickname() {
        return nickname.getNickname();
    }

    public String getPhone() {
        return phone.getPhone();
    }

    public MemberStatus getStatus() {
        return status;
    }

    public String getRole() {
        return role.toString();
    }

    public AuthProvider getAuthProvider() {
        return authProvider;
    }
}
