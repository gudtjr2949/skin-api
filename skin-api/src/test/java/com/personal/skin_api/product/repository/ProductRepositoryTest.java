package com.personal.skin_api.product.repository;

import com.personal.skin_api.JpaAbstractIntegrationTest;
import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.MemberRole;
import com.personal.skin_api.member.repository.entity.MemberStatus;
import com.personal.skin_api.product.repository.entity.Product;
import com.personal.skin_api.product.repository.entity.QProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.personal.skin_api.product.repository.QProductRepository.PRODUCTS_PAGE_SIZE;
import static org.assertj.core.api.Assertions.*;

class ProductRepositoryTest extends JpaAbstractIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void 제품을_생성한다() {
        // given
        Member member = createGeneralMember();

        String productName = "형석이의 스킨";
        String productContent = "아주 예쁜 스킨입니다!";
        String fileUrl = "s3://hyeongseok-skin/fileUrl";
        Long price = 10_000L;

        Product product = Product.builder()
                .member(member)
                .productName(productName)
                .productContent(productContent)
                .fileUrl(fileUrl)
                .price(price)
                .build();

        // when
        Product savedProduct = productRepository.save(product);

        // then
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isEqualTo(product.getId());
    }

    @Test
    void 제품정보를_아이디를_사용해_상세조회한다() {
        // given
        Member member = createGeneralMember();
        Product product = createProduct(member);

        // when
        Optional<Product> byId = productRepository.findById(product.getId());

        // then
        assertThat(byId).isPresent();
        assertThat(byId.get().getId()).isEqualTo(product.getId());
    }
}