package com.personal.skin_api.product.service.dto;

import com.personal.skin_api.product.repository.entity.QProduct;
import com.querydsl.core.types.OrderSpecifier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductSorter {
    VIEWS("views", QProduct.product.productViews.productViews.desc()),
    PRICE_ASC("price_asc", QProduct.product.price.price.asc()),
    PRICE_DESC("price_desc", QProduct.product.price.price.desc());

    private final String sorter;
    private final OrderSpecifier<?> orderSpecifier;

    public static OrderSpecifier<?> getOrderSpecifier(String sorter) {
        for (ProductSorter ps : values()) {
            if (ps.getSorter().equals(sorter)) {
                return ps.getOrderSpecifier();
            }
        }
        return QProduct.product.id.desc(); // 기본 정렬 기준
    }
}