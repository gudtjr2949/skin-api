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

    @Column(name = "PASSWORD")
    private Password password;

    @Column(name = "NICKNAME")
    private String name;

    @Column(name = "PHONE")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "STATUS")
    private MemberStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "ROLE")
    private MemberRole role;

    @Builder
    private Member(Password password, String name, String phone, MemberStatus status, MemberRole role) {
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.status = status;
        this.role = role;
    }
}
