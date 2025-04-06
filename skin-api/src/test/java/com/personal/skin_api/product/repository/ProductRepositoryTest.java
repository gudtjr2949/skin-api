package com.personal.skin_api.product.repository;

import com.personal.skin_api.JpaAbstractIntegrationTest;
import com.personal.skin_api.common.util.MerchantUidGenerator;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.product.repository.entity.Product;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
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

    @Test
    void 제품_정보를_조회할_떄_주문_횟수도_함께_조회한다() {
        // given
        Member member = createGeneralMember();
        Product product = createProduct(member);
        int orderCnt = 5;
        for (int i = 0 ; i < orderCnt ; i++) {
            String orderUid = MerchantUidGenerator.generateMerchantUid();
            Order order = createOrder(member, product, orderUid);
            product.increaseOrder();
        }

        // when
        Optional<Product> byId = productRepository.findById(product.getId());

        // then
        assertThat(byId).isPresent();
        assertThat(byId.get().getOrderCnt()).isEqualTo(orderCnt);
    }
}