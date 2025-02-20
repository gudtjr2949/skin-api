package com.personal.skin_api.member.repository.entity;

import com.personal.skin_api.common.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PASSWORD")
    private String password;

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
}
