package com.personal.skin_api.payment.repository.entity;

import com.personal.skin_api.order.repository.entity.Order;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @Column(name = "IMP_UID")
    private String impUid;

    @Column(name = "PAY_METHOD")
    private String payMethod;

    @Column(name = "PRICE")
    private Long price;

    @Column(name = "PAID_AT")
    private LocalDateTime paidAt;

    @Builder
    private Payment(final String impUid, final String payMethod, final Order order,
                    final Long price, final LocalDateTime paidAt) {
        this.impUid = impUid;
        this.payMethod = payMethod;
        this.order = order;
        this.price = price;
        this.paidAt = paidAt;
    }

    public Long getId() {
        return id;
    }

    public String getImpUid() {
        return impUid;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public Long getPrice() {
        return price;
    }
}
