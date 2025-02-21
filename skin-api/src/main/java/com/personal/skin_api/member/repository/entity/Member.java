package com.personal.skin_api.member.repository.entity;

import com.personal.skin_api.common.entity.BaseEntity;
import com.personal.skin_api.member.repository.entity.member_name.MemberName;
import com.personal.skin_api.member.repository.entity.password.Password;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Embedded
    private Password password;

    @Embedded
    private MemberName memberName;

    @Column(name = "NICKNAME")
    private String nickName;

    @Column(name = "PHONE")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "STATUS")
    private MemberStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "ROLE")
    private MemberRole role;

    @Builder
    private Member(String password, String memberName, String nickName, String phone, MemberStatus status, MemberRole role) {
        this.password = new Password(password);
        this.memberName = new MemberName(memberName);
        this.nickName = nickName;
        this.phone = phone;
        this.status = status;
        this.role = role;
    }
}
