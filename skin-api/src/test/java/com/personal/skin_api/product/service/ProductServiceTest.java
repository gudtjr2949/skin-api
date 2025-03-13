package com.personal.skin_api.product.service;

import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.MemberRole;
import com.personal.skin_api.member.repository.entity.MemberStatus;
import com.personal.skin_api.product.repository.ProductRepository;
import com.personal.skin_api.product.service.dto.request.ProductRegisterServiceRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeAll
    void beforeAll() {
        member = createMember();
    }

    @Test
    void 제품을_등록한다() throws IOException {
        // given
        String productName = "형석이의 스킨";
        String productContent = "아주 예쁜 스킨입니다!";
        ClassPathResource resource = new ClassPathResource("test.zip");
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.zip",
                MediaType.APPLICATION_OCTET_STREAM_VALUE,
                resource.getInputStream()
        );
        int price = 10_000;

        ProductRegisterServiceRequest registerRequest = ProductRegisterServiceRequest.builder()
                .email(member.getEmail())
                .productName(productName)
                .productContent(productContent)
                .file(file)
                .price(price)
                .build();

        // when & then
        assertThatNoException().isThrownBy(() -> productService.registerProduct(registerRequest));
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