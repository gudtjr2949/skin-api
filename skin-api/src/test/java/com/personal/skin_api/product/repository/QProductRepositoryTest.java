package com.personal.skin_api.product.repository;

import com.personal.skin_api.AbstractIntegrationTest;
import com.personal.skin_api.member.repository.entity.Member;

import com.personal.skin_api.product.repository.entity.Product;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.personal.skin_api.product.repository.QProductRepository.PRODUCTS_PAGE_SIZE;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class QProductRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private QProductRepository qProductRepository;

    @Test
    void 제품_리스트_첫_장을_조회한다() {
        // given
        Member member = createGeneralMember();
        for (int i = 0 ; i < PRODUCTS_PAGE_SIZE ; i++) {
            createProduct(member);
        }
        Product lastProduct = createProduct(member);
        for (int i = 1 ; i < PRODUCTS_PAGE_SIZE-1 ; i++) {
            createProduct(member);
        }
        Product firstProduct = createProduct(member);

        List<Product> products = qProductRepository.findProducts(0L, "", "");

        // then
        assertThat(products).hasSize(PRODUCTS_PAGE_SIZE);
        assertThat(products.get(0).getId()).isEqualTo(firstProduct.getId());
        assertThat(products.get(products.size()-1).getId()).isEqualTo(lastProduct.getId());
    }

    @Test
    void 제품_리스트_중간_장을_조회한다() {
        // given
        Member member = createGeneralMember();
        for (int i = 0 ; i < PRODUCTS_PAGE_SIZE ; i++) {
            createProduct(member);
        }
        Product lastProduct = createProduct(member);
        for (int i = 1 ; i < PRODUCTS_PAGE_SIZE-1 ; i++) {
            createProduct(member);
        }
        Product firstProduct = createProduct(member);
        Product lastViewProduct = createProduct(member);
        for (int i = 1 ; i < PRODUCTS_PAGE_SIZE ; i++) {
            createProduct(member);
        }

        // when
        List<Product> products = qProductRepository.findProducts(lastViewProduct.getId(), "", "");

        // then
        assertThat(products).hasSize(PRODUCTS_PAGE_SIZE);
        assertThat(products.get(0).getId()).isEqualTo(firstProduct.getId());
        assertThat(products.get(products.size()-1).getId()).isEqualTo(lastProduct.getId());
    }

    @Test
    void 제품_리스트_마지막_장을_조회한다() {
        // given
        Member member = createGeneralMember();
        Product lastProduct = createProduct(member);
        for (int i = 1 ; i < PRODUCTS_PAGE_SIZE-1 ; i++) {
            createProduct(member);
        }
        Product firstProduct = createProduct(member);
        Product lastViewProduct = createProduct(member);
        for (int i = 1 ; i < PRODUCTS_PAGE_SIZE ; i++) {
            createProduct(member);
        }

        // when
        List<Product> products = qProductRepository.findProducts(lastViewProduct.getId(), "", "");

        // then
        assertThat(products).hasSize(PRODUCTS_PAGE_SIZE);
        assertThat(products.get(0).getId()).isEqualTo(firstProduct.getId());
        assertThat(products.get(products.size()-1).getId()).isEqualTo(lastProduct.getId());
    }

    @Test
    void 제품_리스트_마지막_장을_조회하는데_pageSize보다_적은_수가_조회된다() {
        // given
        Member member = createGeneralMember();
        for (int i = 0 ; i < PRODUCTS_PAGE_SIZE*2 ; i++) {
            createProduct(member);
        }

        // when
        List<Product> products = qProductRepository.findProducts(PRODUCTS_PAGE_SIZE - 1L, "", "");

        // then
        assertThat(products.size()).isLessThan(PRODUCTS_PAGE_SIZE);
    }

    @Test
    void 제품을_높은_가격_순으로_조회한다() {
        // given
        Member member = createGeneralMember();
        String sorter = "price_desc";

        Long firstPrice = 30_000L;
        Long secondPrice = 20_000L;
        Long thirdPrice = 10_000L;
        Product firstProduct = createProductWithPrice(member, firstPrice);
        Product secondProduct = createProductWithPrice(member, secondPrice);
        Product thirdProduct = createProductWithPrice(member, thirdPrice);

        // when
        List<Product> products = qProductRepository.findProducts(0L, sorter, null);

        // then
        assertThat(products).hasSize(3);
        assertThat(products.get(0).getId()).isEqualTo(firstProduct.getId());
        assertThat(products.get(products.size()-1).getId()).isEqualTo(thirdProduct.getId());
    }

    @Test
    void 제품을_낮은_가격_순으로_조회한다() {
        // given
        Member member = createGeneralMember();
        String sorter = "price_asc";

        Long firstPrice = 10_000L;
        Long secondPrice = 20_000L;
        Long thirdPrice = 30_000L;
        Product firstProduct = createProductWithPrice(member, firstPrice);
        Product secondProduct = createProductWithPrice(member, secondPrice);
        Product thirdProduct = createProductWithPrice(member, thirdPrice);

        // when
        List<Product> products = qProductRepository.findProducts(0L, sorter, null);

        // then
        assertThat(products).hasSize(3);
        assertThat(products.get(0).getId()).isEqualTo(firstProduct.getId());
        assertThat(products.get(products.size()-1).getId()).isEqualTo(thirdProduct.getId());
    }

    @Test
    void 제품을_주문이_많은_순으로_조회한다() {
        // given

        // when

        // then
    }

    @Test
    void 제품을_후기가_많은_순으로_조회한다() {
        // given

        // when

        // then
    }
}