package com.personal.skin_api.product.repository;

import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.product.repository.entity.Product;
import com.personal.skin_api.product.repository.entity.ProductStatus;
import com.personal.skin_api.product.repository.entity.QProduct;
import com.personal.skin_api.product.service.dto.ProductSorter;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QProductRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    private static final String ORDERS = "orders", REVIEWS = "reviews";
    public static final int PRODUCTS_PAGE_SIZE = 5;

    public QProductRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    // TODO : RECENT를 제외한 No Offset 기준 찾기
    public List<Product> findProducts(Long productId, String sorter, String keyword) {
        OrderSpecifier<?> orderSpecifier = ProductSorter.getOrderSpecifier(sorter);
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(QProduct.product.productStatus.eq(ProductStatus.ACTIVE));

        // TODO : 리팩토링 필요
        if (productId > 0 && ProductSorter.RECENT.getSorter().equals(sorter)) {
            builder.and(QProduct.product.id.lt(productId));
        }

        if (keyword != null) {
            builder.and(QProduct.product.productName.productName.contains(keyword));
            builder.and(QProduct.product.productContent.productContent.contains(keyword));
        }

        List<Product> findProducts = queryFactory
                .selectFrom(QProduct.product)
                .where(builder)
                .orderBy(orderSpecifier)
                .limit(PRODUCTS_PAGE_SIZE)
                .fetch();

        return findProducts;
    }

    public List<Product> findMyProducts(Long productId, Member member) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(QProduct.product.productStatus.eq(ProductStatus.ACTIVE));
        if (productId > 0) {
            builder.and(QProduct.product.id.lt(productId));
        }
        builder.and(QProduct.product.member.eq(member));

        List<Product> findProducts = queryFactory
                .selectFrom(QProduct.product)
                .where(builder)
                .orderBy(QProduct.product.id.desc())
                .limit(PRODUCTS_PAGE_SIZE)
                .fetch();

        return findProducts;
    }
}
