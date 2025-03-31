package com.personal.skin_api;

import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.MemberRole;
import com.personal.skin_api.member.repository.entity.MemberStatus;
import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.product.repository.entity.Product;

public class TestEntityFactory {

    /**
     * 회원 정보
     */
    private static String email = "asd123@naver.com";
    private static String password = "asd1234!";
    private static String memberName = "홍길동";
    private static String nickname = "길동짱짱";
    private static String phone = "01012345678";


    /**
     * 제품 정보
     */
    private static String productName = "형석이의 스킨";
    private static String productContent = "아주 예쁜 스킨입니다!";
    private static String fileUrl = "s3://hyeongseok-skin/fileUrl";
    private static Long price = 10_000L;

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

    public static Product createProduct(Member member) {
        return Product.builder()
                .member(member)
                .productName(productName)
                .productContent(productContent)
                .fileUrl(fileUrl)
                .price(price)
                .build();
    }

    public static Order createOrder(final Member member, final Product product, final String orderUid) {
        return Order.createBeforePayOrder(member, product, orderUid);
    }
}
