package com.personal.skin_api.order.repository;

import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.order.repository.entity.OrderStatus;
import com.personal.skin_api.order.repository.entity.QOrder;

import com.personal.skin_api.review.repository.entity.QReview;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QOrderRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    public static final int ORDER_PAGE_SIZE = 5;

    public QOrderRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Order> findMyOrderList(Long orderId, Member member, String keyword, int year) {
        BooleanBuilder builder = new BooleanBuilder();

        if (orderId > 0) {
            builder.and(QOrder.order.id.lt(orderId));
        }

        if (!("".equals(keyword) || keyword == null)) {
            builder.and(QOrder.order.product.productName.productName.contains(keyword));
        }

        builder.and(QOrder.order.member.eq(member));
        builder.and(QOrder.order.createdAt.year().eq(year));

        List<Order> findOrders = queryFactory
                .selectFrom(QOrder.order)
                .where(builder)
                .orderBy(QOrder.order.id.desc())
                .limit(ORDER_PAGE_SIZE)
                .fetch();

        return findOrders;
    }

    public List<Order> findWritableReviewsOrderList(Long orderId, Member member) {
        BooleanBuilder builder = new BooleanBuilder();

        if (orderId > 0) {
            builder.and(QOrder.order.id.lt(orderId));
        }

        builder.and(QOrder.order.member.eq(member));
        builder.and(QOrder.order.orderStatus.eq(OrderStatus.PAID));
        builder.and(QOrder.order.review.isNull());

        List<Order> findOrders = queryFactory
                .selectFrom(QOrder.order)
                .where(builder)
                .orderBy(QOrder.order.id.desc())
                .limit(ORDER_PAGE_SIZE)
                .fetch();

        return findOrders;
    }
}
