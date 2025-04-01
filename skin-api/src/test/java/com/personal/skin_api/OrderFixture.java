package com.personal.skin_api;

import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.product.repository.entity.Product;

public class OrderFixture {
    public static Order createOrder(final Member member, final Product product, final String orderUid) {
        return Order.createBeforePayOrder(member, product, orderUid);
    }
}
