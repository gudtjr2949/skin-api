package com.personal.skin_api.product.service;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.MemberRole;
import com.personal.skin_api.member.repository.entity.MemberStatus;
import com.personal.skin_api.product.repository.ProductRepository;
import com.personal.skin_api.product.repository.entity.Product;
import com.personal.skin_api.product.service.dto.request.ProductFindListServiceRequest;
import com.personal.skin_api.product.service.dto.request.ProductFindMyListServiceRequest;
import com.personal.skin_api.product.service.dto.request.ProductModifyServiceRequest;
import com.personal.skin_api.product.service.dto.request.ProductRegisterServiceRequest;
import com.personal.skin_api.product.service.dto.response.ProductDetailResponse;
import com.personal.skin_api.product.service.dto.response.ProductListResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.Optional;
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

    @Test
    void 제품_상세_정보를_조회한다() {
        // given
        Product product = products.get(10);

        // when
        ProductDetailResponse findDetailProduct = productService.findProductDetail(product.getId());

        // then
        assertThat(findDetailProduct.getProductId()).isEqualTo(product.getId());
        assertThat(findDetailProduct.getProductName()).isEqualTo(product.getProductName());
        assertThat(findDetailProduct.getProductContent()).isEqualTo(product.getProductContent());
    }

    @Test
    void 제품_상세_정보를_조회할_때_없는_제품ID가_입력되면_예외가_발생한다() {
        // given
        Long smallProductId = 0L;
        Long largeProductId = 1_000_000_000L;
        List<Long> noProductIds = List.of(smallProductId, largeProductId);

        // when & then
        noProductIds.stream().forEach(productId -> assertThatThrownBy(() -> productService.findProductDetail(productId))
                .isInstanceOf(RestApiException.class));
    }

    @Test
    void 제품_상세_정보_조회를_성공하면_조회수가_1_증가한다() {
        // given
        Product product = products.get(0);

        int beforeProductViews = product.getProductViews();

        // when
        ProductDetailResponse findDetailProduct = productService.findProductDetail(product.getId());

        // then
        assertThat(findDetailProduct.getProductViews()).isEqualTo(beforeProductViews+1);
    }

    @Test
    void 제품_정보를_수정한다() throws IOException {
        // given
        Product product = products.get(0);
        String originFileUrl = product.getFileUrl();

        String newProductName = "제품명 수정";
        String newProductContent = "제품 내용 수정";
        ClassPathResource resource = new ClassPathResource("new_test.zip");
        MockMultipartFile newFile = new MockMultipartFile(
                "file",
                "new_test.zip",
                MediaType.APPLICATION_OCTET_STREAM_VALUE,
                resource.getInputStream()
        );
        int newPrice = product.getPrice() + 10_000;

        ProductModifyServiceRequest request = ProductModifyServiceRequest.builder()
                .productId(product.getId())
                .email(product.getMember())
                .newProductName(newProductName)
                .newProductContent(newProductContent)
                .newFile(newFile)
                .newPrice(newPrice)
                .build();

        // when
        productService.modifyProduct(request);
        Optional<Product> modifiedProduct = productRepository.findById(product.getId());

        // then
        assertThat(modifiedProduct).isPresent();
        assertThat(modifiedProduct.get().getProductName()).isEqualTo(newProductName);
        assertThat(modifiedProduct.get().getProductContent()).isEqualTo(newProductContent);
        assertThat(modifiedProduct.get().getPrice()).isEqualTo(newPrice);
        assertThat(modifiedProduct.get().getFileUrl()).isNotEqualTo(originFileUrl);
    }
    
    @Test
    void 새로운_제품_파일이_null인_경우_파일_경로는_변경하지_않는다() {
        // given
        Product product = products.get(0);
        String originFileUrl = product.getFileUrl();

        String newProductName = "제품명 수정";
        String newProductContent = "제품 내용 수정";
        int newPrice = product.getPrice() + 10_000;

        ProductModifyServiceRequest request = ProductModifyServiceRequest.builder()
                .productId(product.getId())
                .email(product.getMember())
                .newProductName(newProductName)
                .newProductContent(newProductContent)
                .newFile(null)
                .newPrice(newPrice)
                .build();

        // when
        productService.modifyProduct(request);
        Optional<Product> modifiedProduct = productRepository.findById(product.getId());

        // then
        assertThat(modifiedProduct).isPresent();
        assertThat(modifiedProduct.get().getFileUrl()).isEqualTo(originFileUrl);
    }

    @Test
    void 제품_수정_시_해당_제품_등록자가_아닌_경우_예외가_발생한다() {
        // given
        Product product = products.get(0);

        String newProductName = "제품명 수정";
        String newProductContent = "제품 내용 수정";
        int newPrice = product.getPrice() + 10_000;

        ProductModifyServiceRequest request = ProductModifyServiceRequest.builder()
                .productId(product.getId())
                .email("wrong123@naver.com")
                .newProductName(newProductName)
                .newProductContent(newProductContent)
                .newFile(null)
                .newPrice(newPrice)
                .build();

        // when & then
        assertThatThrownBy(() -> productService.modifyProduct(request))
                .isInstanceOf(RestApiException.class);
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