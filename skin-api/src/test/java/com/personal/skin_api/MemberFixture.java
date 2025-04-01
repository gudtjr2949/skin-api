package com.personal.skin_api;

import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.MemberRole;
import com.personal.skin_api.member.repository.entity.MemberStatus;
import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.product.repository.entity.Product;

public class MemberFixture {

    /**
     * 회원 정보
     */
    private static String email = "asd123@naver.com";
    private static String password = "asd1234!";
    private static String memberName = "홍길동";
    private static String nickname = "길동짱짱";
    private static String phone = "01012345678";


    public static Member createGeneralMember() {
        return Member.builder()
                .email(email)
                .password(password)
                .memberName(memberName)
                .nickname(nickname)
                .phone(phone)
                .status(MemberStatus.ACTIVE)
                .role(MemberRole.GENERAL)
                .build();
    }
}
