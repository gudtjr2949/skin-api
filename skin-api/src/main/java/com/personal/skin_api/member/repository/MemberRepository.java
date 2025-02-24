package com.personal.skin_api.member.repository;

import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.email.Email;
import com.personal.skin_api.member.repository.entity.member_name.MemberName;
import com.personal.skin_api.member.repository.entity.password.Password;
import com.personal.skin_api.member.repository.entity.phone.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByEmailAndPassword(Email email, Password password);
    Optional<Member> findMemberByMemberNameAndPhone(MemberName memberName, Phone phone);
    Optional<Member> findMemberByEmailAndPhone(Email email, Phone phone);
}
