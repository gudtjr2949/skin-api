package com.personal.skin_api.product.service.dto;

import com.personal.skin_api.order.repository.entity.QOrder;
import com.personal.skin_api.product.repository.entity.QProduct;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum ProductSorter {
    RECENT("recent", List.of(QProduct.product.id.desc())),
    ORDERS("orders", List.of(QProduct.product.orderCnt.desc(), QProduct.product.id.asc())),
    REVIEWS("reviews", List.of(QProduct.product.reviewCnt.desc(), QProduct.product.id.asc())),
    VIEWS("views", List.of(QProduct.product.productViews.productViews.desc(), QProduct.product.id.asc())),
    PRICE_ASC("price_asc", List.of(QProduct.product.price.price.asc(), QProduct.product.id.asc())),
    PRICE_DESC("price_desc", List.of(QProduct.product.price.price.desc(), QProduct.product.id.asc()));

    private final String sorter;
    private final List<OrderSpecifier<?>> orderSpecifiers;

    public static List<OrderSpecifier<?>> getOrderSpecifiers(String sorter) {
        for (ProductSorter ps : values()) {
            if (ps.getSorter().equals(sorter)) {
                return ps.getOrderSpecifiers();
            }
        }
        return List.of(QProduct.product.id.desc()); // 기본 정렬 기준
    }
}