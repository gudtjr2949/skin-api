package com.personal.skin_api.common.batch;

import com.personal.skin_api.product.repository.entity.Product;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ProductItemProcessor implements ItemProcessor<Product, Product> {

    @Override
    public Product process(Product product) {
        // TODO : 만약 product 엔티티의 데이터를 전처리하고 싶을 경우 수정하는 코드 입력
        return product;
    }
}