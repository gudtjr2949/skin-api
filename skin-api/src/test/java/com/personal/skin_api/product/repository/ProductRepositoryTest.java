package com.personal.skin_api.product.repository;

import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.MemberRole;
import com.personal.skin_api.member.repository.entity.MemberStatus;
import com.personal.skin_api.product.repository.entity.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@DataJpaTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 인스턴스 단위로 실행 (static 필요 없음)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeAll
    void setUp() {
        this.member = createMember();
    }

    @Test
    void 생성된_Member_객체를_확인한다() {
        assertThat(member.getId()).isNotNull();
    }

    @Test
    void 제품을_생성한다() {
        // given
        Product product = createProduct();

        // when
        Product savedProduct = productRepository.save(product);

        // then
        assertThat(product.getId()).isNotNull();
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getId()).isEqualTo(product.getId());
    }

    @Test
    void 제품정보를_아이디를_사용해_상세조회한다() {
        // given
        Product product = createProduct();
        productRepository.save(product);

        // when
        Optional<Product> byId = productRepository.findById(product.getId());

        // then
        assertThat(byId).isPresent();
        assertThat(byId.get().getId()).isEqualTo(product.getId());
    }

    private Product createProduct() {
        String productName = "형석이의 스킨";
        String productContent = "아주 예쁜 스킨입니다!";
        String fileUrl = "s3://hyeongseok-skin/fileUrl";
        int price = 10_000;

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

        return memberRepository.save(Member.builder()
                .email(email)
                .password(password)
                .memberName(memberName)
                .nickname(nickname)
                .phone(phone)
                .status(status)
                .role(role)
                .build());
    }
}