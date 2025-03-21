package com.personal.skin_api.order.repository.entity;

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

    @Column(name = "IMP_UID")
    private String impUid;

    @Column(name = "PAY_METHOD")
    private String payMethod;

    @Column(name = "PAY_INFO")
    private String payInfo;

    @Column(name = "PRICE")
    private Long price;

    @Column(name = "PAID_AT")
    private LocalDateTime paidAt;

    @Builder
    private Payment(final String impUid, final String payMethod, final String payInfo,
                    final Long price, final LocalDateTime paidAt) {
        this.impUid = impUid;
        this.payMethod = payMethod;
        this.payInfo = payInfo;
        this.price = price;
        this.paidAt = paidAt;
    }
}
