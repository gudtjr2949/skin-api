package com.personal.skin_api.member.repository.entity;

import com.personal.skin_api.common.entity.BaseEntity;
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

    @Column(name = "NAME")
    private String name;

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
    private Member(Password password, String name, String nickName, String phone, MemberStatus status, MemberRole role) {
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.phone = phone;
        this.status = status;
        this.role = role;
    }
}
