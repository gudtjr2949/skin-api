package com.personal.skin_api.product.repository;

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

@Slf4j
@DataJpaTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

    private JPAQueryFactory queryFactory;

    private Member member;

    private List<Product> products;

    @BeforeEach
    void beforeEach() {
        queryFactory = new JPAQueryFactory(em);
    }

    @BeforeAll
    void setUp() {
        this.member = createMember();
        products = Stream.generate(this::createProduct)
                .limit(20)
                .collect(Collectors.toList());
        productRepository.saveAll(products);
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

    @Test
    void 제품_리스트_첫_장을_조회한다() {
        // given
        long productId = 0L; // 첫 장을 조회하기 때문에 기준이 되는 productId가 없음

        BooleanBuilder builder = new BooleanBuilder();
        if (productId > 0) {
            builder.and(QProduct.product.id.lt(productId));
        }

        // when
        List<Product> findProducts = queryFactory
                .selectFrom(QProduct.product)
                .where(builder)
                .orderBy(QProduct.product.id.desc())
                .limit(PRODUCTS_PAGE_SIZE)
                .fetch();

        int firstIdx = products.size()-1; // 내림차순이기 때문에 products의 가장 마지막 객체가 가장 처음으로 조회됨
        int lastIdx = firstIdx - (PRODUCTS_PAGE_SIZE - 1);

        // then
        assertThat(findProducts).hasSize(PRODUCTS_PAGE_SIZE);
        assertThat(products.get(firstIdx).getId()).isEqualTo(findProducts.get(0).getId());
        assertThat(products.get(lastIdx).getId()).isEqualTo(findProducts.get(PRODUCTS_PAGE_SIZE-1).getId());
    }

    @Test
    void 제품_리스트_중간_장을_조회한다() {
        // given
        long productId = (long) (products.size() / 2); // 이전까지 조회한 제품 중 가장 마지막 제품 (productId가 가장 작은 제품)

        // when
        List<Product> findProducts = queryFactory
                .selectFrom(QProduct.product)
                .where(QProduct.product.id.lt(productId))
                .orderBy(QProduct.product.id.desc())
                .limit(PRODUCTS_PAGE_SIZE)
                .fetch();

        int firstIdx = (products.size() / 2) - 2;
        int lastIdx = firstIdx - (PRODUCTS_PAGE_SIZE - 1);
        
        // then
        assertThat(findProducts).hasSize(PRODUCTS_PAGE_SIZE);
        assertThat(products.get(firstIdx).getId()).isEqualTo(findProducts.get(0).getId());
        assertThat(products.get(lastIdx).getId()).isEqualTo(findProducts.get(PRODUCTS_PAGE_SIZE-1).getId());
    }

    @Test
    void 제품_리스트_마지막_장을_조회한다() {
        // given
        long productId = (long) PRODUCTS_PAGE_SIZE + 1; // 마지막 글 조회에 pageSize가 모두 조회되도록 pageSize에 1을 더함

        // when
        List<Product> findProducts = queryFactory
                .selectFrom(QProduct.product)
                .where(QProduct.product.id.lt(productId))
                .orderBy(QProduct.product.id.desc())
                .limit(PRODUCTS_PAGE_SIZE)
                .fetch();

        int firstIdx = (int) productId - 2;
        int lastIdx = firstIdx - (PRODUCTS_PAGE_SIZE - 1);

        // then
        assertThat(findProducts).hasSize(PRODUCTS_PAGE_SIZE);
        assertThat(products.get(firstIdx).getId()).isEqualTo(findProducts.get(0).getId());
        assertThat(products.get(lastIdx).getId()).isEqualTo(findProducts.get(PRODUCTS_PAGE_SIZE-1).getId());
    }

    @Test
    void 제품_리스트_마지막_장을_조회하는데_pageSize보다_적은_수가_조회된다() {
        // given
        long productId = PRODUCTS_PAGE_SIZE-1;

        // when
        List<Product> findProducts = queryFactory
                .selectFrom(QProduct.product)
                .where(QProduct.product.id.lt(productId))
                .orderBy(QProduct.product.id.desc())
                .limit(PRODUCTS_PAGE_SIZE)
                .fetch();

        // then
        assertThat(findProducts.size()).isLessThan(PRODUCTS_PAGE_SIZE);
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