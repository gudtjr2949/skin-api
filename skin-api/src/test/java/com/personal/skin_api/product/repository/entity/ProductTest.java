package com.personal.skin_api.product.repository.entity;

import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.MemberRole;
import com.personal.skin_api.member.repository.entity.MemberStatus;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ProductTest {

    @Test
    void 제품이_생성된다() {
        // given
        Member member = createMember();
        String productName = "형석이의 스킨";
        String productContent = "아주 예쁜 스킨입니다!";
        String fileUrl = "s3://hyeongseok-skin/fileUrl";
        Long price = 10_000L;

        // when
        Product product = createProduct(member, productName, productContent, fileUrl, price);

        // then
        assertThat(product.getMember()).isEqualTo(member.getEmail());
        assertThat(product.getProductName()).isEqualTo(productName);
        assertThat(product.getProductContent()).isEqualTo(productContent);
        assertThat(product.getFileUrl()).isEqualTo(fileUrl);
        assertThat(product.getPrice()).isEqualTo(price);
    }
    
    @Test
    void 제품정보를_수정한다() {
        // given
        Member member = createMember();
        Product product = createProductNoParameter(member);
        String newProductName = product.getProductName() + "2";
        String newProductContent = product.getProductContent() + "2";
        String newFileUrl = product.getFileUrl() + "2";
        Long newPrice = product.getPrice() + 10_000L;

        // when
        product.modifyProduct(newProductName, newProductContent, newFileUrl, newPrice);

        // then
        assertThat(product.getProductName()).isEqualTo(newProductName);
        assertThat(product.getProductContent()).isEqualTo(newProductContent);
        assertThat(product.getFileUrl()).isEqualTo(newFileUrl);
        assertThat(product.getPrice()).isEqualTo(newPrice);
    }
    
    @Test
    void 제품이_삭제되면_상태가_REMOVED로_변한다() {
        // given
        Member member = createMember();
        Product product = createProductNoParameter(member);
        
        // when
        product.deleteProduct();
        
        // then
        assertThat(product.getProductStatus()).isEqualTo(ProductStatus.DELETED);
    }

    @Test
    void 제품이_신고된다면_상태가_REPORTED로_변한다() {
        // given
        Member member = createMember();
        Product product = createProductNoParameter(member);

        // when
        product.reportProduct();

        // then
        assertThat(product.getProductStatus()).isEqualTo(ProductStatus.REPORTED);
    }

    private static Product createProduct(Member member, String productName,
                                         String productContent, String fileUrl, Long price) {
        return Product.builder()
                .member(member)
                .productName(productName)
                .productContent(productContent)
                .fileUrl(fileUrl)
                .price(price)
                .build();
    }

    private static Product createProductNoParameter(Member member) {
        String productName = "형석이의 스킨";
        String productContent = "아주 예쁜 스킨입니다!";
        String fileUrl = "s3://hyeongseok-skin/fileUrl";
        Long price = 10_000L;

        return Product.builder()
                .member(member)
                .productName(productName)
                .productContent(productContent)
                .fileUrl(fileUrl)
                .price(price)
                .build();
    }

    private Member createMember() {
        String email = "asd123@naver.com";
        String password = "asd1234!";
        String memberName = "홍길동";
        String nickname = "길동짱짱";
        String phone = "01012345678";
        MemberStatus status = MemberStatus.ACTIVE;
        MemberRole role = MemberRole.GENERAL;

        return Member.builder()
                .email(email)
                .password(password)
                .memberName(memberName)
                .nickname(nickname)
                .phone(phone)
                .status(status)
                .role(role)
                .build();
    }
}