package com.personal.skin_api.order.repository.entity;

public enum OrderStatus {
    PAID,   // 완료된 주문
    WAITING, // 대기중인 주문
    CANCELED, // 취소된 주문
}