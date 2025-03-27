package com.personal.skin_api.order.repository.entity;

import com.personal.skin_api.common.entity.BaseEntity;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.product.repository.entity.Product;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ORDERS")
@NoArgsConstructor
public class Order extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ORDER_UID")
    private String orderUid;

    @ManyToOne
    @JoinColumn(name = "ORDERER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @OneToOne(mappedBy = "order")
    private Payment payment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "ORDER_STATUS")
    private OrderStatus orderStatus;

    public void paidOrder() {
        orderStatus = OrderStatus.PAID;
    }

    public void cancelOrder() {
        orderStatus = OrderStatus.CANCELED;
    }

    public static Order createBeforePayOrder(final Member member, final Product product, final String orderUid) {
        return new Order(member, product, orderUid, OrderStatus.WAITING);
    }

    private Order(final Member member, final Product product, final String orderUid, final OrderStatus orderStatus) {
        this.member = member;
        this.product = product;
        this.orderUid = orderUid;
        this.orderStatus = orderStatus;
    }


    public Long getId() {
        return id;
    }

    public String getOrderUid() {
        return orderUid;
    }

    public String getMember() {
        return member.getEmail();
    }

    public String getProductName() {
        return product.getProductName();
    }

    public String getPayMethod() {
        return payment.getPayMethod();
    }

    public Long getPrice() {
        return payment.getPrice();
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
