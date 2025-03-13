package com.personal.skin_api.product.repository;

import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.product.repository.entity.Product;
import com.personal.skin_api.product.repository.entity.QProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QProductRepository {

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

    private static final int PRODUCTS_PAGE_SIZE = 5;

    public List<Product> findProducts(Long productId, String keyword) {
        BooleanBuilder builder = new BooleanBuilder();
        if (productId > 0) {
            builder.and(QProduct.product.id.lt(productId));
        }

        if (keyword != null) {
            builder.and(QProduct.product.productName.productName.contains(keyword));
            builder.and(QProduct.product.productContent.productContent.contains(keyword));
        }

        List<Product> findProducts = queryFactory
                .selectFrom(QProduct.product)
                .where(builder)
                .orderBy(QProduct.product.id.desc())
                .limit(PRODUCTS_PAGE_SIZE)
                .fetch();

        return findProducts;
    }

    public List<Product> findMyProducts(Long productId, Member member) {
        BooleanBuilder builder = new BooleanBuilder();
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
