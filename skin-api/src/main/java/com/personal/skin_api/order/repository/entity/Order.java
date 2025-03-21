package com.personal.skin_api.order.repository.entity;

import com.personal.skin_api.common.entity.BaseEntity;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.product.repository.entity.Product;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ORDERS")
@NoArgsConstructor
public class Order extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ORDERER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @OneToOne
    @JoinColumn(name = "PAYMENT_ID")
    private Payment payment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "ORDER_STATUS")
    private OrderStatus orderStatus;

    public void cancelOrder() {
        orderStatus = OrderStatus.CANCELED;
    }

    public static Order completedPayOrder(final Member member, final Product product, final Payment payment) {
        return Order.builder()
                .member(member)
                .product(product)
                .payment(payment)
                .orderStatus(OrderStatus.PAID)
                .build();
    }

    @Builder
    private Order(final Member member, final Product product, final Payment payment, final OrderStatus orderStatus) {
        this.member = member;
        this.product = product;
        this.payment = payment;
        this.orderStatus = orderStatus;
    }


    public Long getId() {
        return id;
    }

    public String getMember() {
        return member.getEmail();
    }

    public Long getProduct() {
        return product.getId();
    }

    public Long getPayment() {
        return payment.getId();
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
