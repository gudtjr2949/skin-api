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

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class QProductRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    public static final int PRODUCTS_PAGE_SIZE = 5;

    public QProductRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    // TODO : 리팩토링 상당히 필요!!
    public List<Product> findProducts(Long productId, String sorter, String keyword, Long lastSortValue) {
        List<OrderSpecifier<?>> orderSpecifier = ProductSorter.getOrderSpecifiers(sorter);
        BooleanBuilder where = new BooleanBuilder();

        where.and(QProduct.product.productStatus.eq(ProductStatus.ACTIVE));

        if (ProductSorter.RECENT.getSorter().equals(sorter)) {
            where.and(QProduct.product.id.lt(productId));
        } else if (lastSortValue > 0L && ProductSorter.ORDERS.getSorter().equals(sorter)) {
            where.and(
                    QProduct.product.orderCnt.lt(lastSortValue)
                            .or(QProduct.product.orderCnt.eq(Long.valueOf(lastSortValue).intValue())
                                    .and(QProduct.product.id.gt(productId)))
            );
        } else if (lastSortValue > 0L && ProductSorter.REVIEWS.getSorter().equals(sorter)) {
            where.and(
                    QProduct.product.reviewCnt.lt(lastSortValue)
                            .or(QProduct.product.reviewCnt.eq(Long.valueOf(lastSortValue).intValue())
                                    .and(QProduct.product.id.gt(productId)))
            );
        } else if (lastSortValue > 0L && ProductSorter.PRICE_ASC.getSorter().equals(sorter)) {
            where.and(
                    QProduct.product.price.price.gt(lastSortValue)
                            .or(QProduct.product.price.price.eq(lastSortValue)
                                    .and(QProduct.product.id.gt(productId)))
            );
        } else if (lastSortValue > 0L && ProductSorter.PRICE_DESC.getSorter().equals(sorter)) {
            where.and(
                    QProduct.product.price.price.lt(lastSortValue)
                            .or(QProduct.product.price.price.eq(lastSortValue)
                                    .and(QProduct.product.id.gt(productId)))
            );
        }

        if (keyword != null) {
            where.and(QProduct.product.productName.productName.contains(keyword));
            where.and(QProduct.product.productContent.productContent.contains(keyword));
        }

        List<Product> findProducts = queryFactory
                .selectFrom(QProduct.product)
                .where(where)
                .orderBy(orderSpecifier.toArray(new OrderSpecifier[0]))
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