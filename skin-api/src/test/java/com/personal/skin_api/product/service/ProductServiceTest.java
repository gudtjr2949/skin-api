package com.personal.skin_api.product.service;

import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.MemberRole;
import com.personal.skin_api.member.repository.entity.MemberStatus;
import com.personal.skin_api.product.repository.ProductRepository;
import com.personal.skin_api.product.repository.entity.Product;
import com.personal.skin_api.product.service.dto.request.ProductFindListServiceRequest;
import com.personal.skin_api.product.service.dto.request.ProductFindMyListServiceRequest;
import com.personal.skin_api.product.service.dto.request.ProductRegisterServiceRequest;
import com.personal.skin_api.product.service.dto.response.ProductListResponse;
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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.personal.skin_api.product.repository.QProductRepository.PRODUCTS_PAGE_SIZE;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    private Member member1, member2;

    private List<Product> products;

    private int PRODUCTS_LENGTH = 20;

    @BeforeAll
    void beforeAll() {
        member1 = createMember("asd123@naver.com");
        member2 = createMember("qwe567@naver.com");
        products = Stream.generate(this::createProduct)
                .limit(PRODUCTS_LENGTH)
                .collect(Collectors.toList());
        productRepository.saveAll(products);
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
                .email(member1.getEmail())
                .productName(productName)
                .productContent(productContent)
                .file(file)
                .price(price)
                .build();

        // when & then
        assertThatNoException().isThrownBy(() -> productService.registerProduct(registerRequest));
    }
    
    @Test
    void 제품_리스트를_조회한다() {
        // given
        ProductFindListServiceRequest request = ProductFindListServiceRequest.builder()
                .productId(10L)
                .keyword(null)
                .build();

        // when
        ProductListResponse findProducts = productService.findProducts(request);
        
        // then
        assertThat(findProducts.getProductResponses()).hasSize(PRODUCTS_PAGE_SIZE);
    }

    @Test
    void 제품_리스트의_첫_장을_조회한다() {
        // given
        ProductFindListServiceRequest request = ProductFindListServiceRequest.builder()
                .productId(0L)
                .keyword(null)
                .build();

        // when
        ProductListResponse findProducts = productService.findProducts(request);

        // then
        assertThat(findProducts.getProductResponses()).hasSize(PRODUCTS_PAGE_SIZE);
        assertThat(findProducts.getProductResponses().get(0).getProductId()).isEqualTo(PRODUCTS_LENGTH);
        assertThat(findProducts.getProductResponses().get(PRODUCTS_PAGE_SIZE-1).getProductId()).isEqualTo(PRODUCTS_LENGTH - PRODUCTS_PAGE_SIZE + 1);
    }

    @Test
    void 제품_리스트의_마지막_장을_조회하면_데이터가_조회되지_않는다() {
        // given
        ProductFindListServiceRequest request = ProductFindListServiceRequest.builder()
                .productId(1L)
                .keyword(null)
                .build();

        // when
        ProductListResponse findProducts = productService.findProducts(request);

        // then
        assertThat(findProducts.getProductResponses()).hasSize(0);
    }
    
    @Test
    void 로그인한_사용자가_등록한_제품_리스트를_조회한다() {
        // given
        ProductFindMyListServiceRequest member1Request = ProductFindMyListServiceRequest.builder()
                .productId(0L)
                .email(member1.getEmail())
                .build();

        ProductFindMyListServiceRequest member2Request = ProductFindMyListServiceRequest.builder()
                .productId(0L)
                .email(member2.getEmail())
                .build();
        
        // when
        ProductListResponse member1Products = productService.findMyProducts(member1Request);
        ProductListResponse member2Products = productService.findMyProducts(member2Request);

        // then
        assertThat(member1Products.getProductResponses()).hasSize(PRODUCTS_PAGE_SIZE);
        assertThat(member2Products.getProductResponses()).hasSize(0);
    }

    private Product createProduct() {
        String productName = "형석이의 스킨";
        String productContent = "아주 예쁜 스킨입니다!";
        String fileUrl = "s3://hyeongseok-skin/fileUrl";
        int price = 10_000;

        return Product.builder()
                .member(member1)
                .productName(productName)
                .productContent(productContent)
                .fileUrl(fileUrl)
                .price(price)
                .build();
    }

    private Member createMember(String email) {
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