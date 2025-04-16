package com.personal.skin_api.order.repository.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    PAID("결제 완료"),   // 완료된 주문
    WAITING("결제 대기중"), // 대기중인 주문
    CANCELED("결제 취소"), // 취소된 주문
    ;

    private final String message;
}