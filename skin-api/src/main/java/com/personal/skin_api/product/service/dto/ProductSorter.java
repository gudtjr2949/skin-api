package com.personal.skin_api.product.service.dto;

import com.personal.skin_api.order.repository.entity.QOrder;
import com.personal.skin_api.product.repository.entity.QProduct;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductSorter {
    RECENT("recent", QProduct.product.id.desc(), QProduct.product.id),
    ORDERS("orders", QProduct.product.orderCnt.desc(), QProduct.product.orderCnt),
    REVIEWS("reviews", QProduct.product.reviewCnt.desc(), QProduct.product.reviewCnt),
    VIEWS("views", QProduct.product.productViews.productViews.desc(), QProduct.product.productViews.productViews),
    PRICE_ASC("price_asc", QProduct.product.price.price.asc(), QProduct.product.price.price),
    PRICE_DESC("price_desc", QProduct.product.price.price.desc(), QProduct.product.price.price);


    private final String sorter;
    private final OrderSpecifier<?> orderSpecifier;
    private final ComparableExpressionBase<?> sortField;

    public static ProductSorter from(String sorter) {
        for (ProductSorter ps : values()) {
            if (ps.getSorter().equals(sorter)) {
                return ps;
            }
        }
        return RECENT; // 기본 정렬 기준
    }
}