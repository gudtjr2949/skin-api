package com.personal.skin_api.order.controller;

import com.personal.skin_api.common.dto.CommonResponse;
import com.personal.skin_api.order.service.OrderService;
import com.personal.skin_api.order.service.dto.request.OrderCreateBeforePaidServiceRequest;
import com.personal.skin_api.order.service.dto.request.OrderCreateTableServiceRequest;
import com.personal.skin_api.order.service.dto.response.OrderCreateTableResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create-table")
    public ResponseEntity<OrderCreateTableResponse> createOrderTable(@RequestParam Long productId,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        OrderCreateTableResponse response = orderService.createOrderTable(OrderCreateTableServiceRequest.builder()
                .email(userDetails.getUsername())
                .productId(productId)
                .build());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createOrder(@RequestParam Long productId,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        String orderUid = orderService.createBeforePaidOrder(OrderCreateBeforePaidServiceRequest.builder()
                .productId(productId)
                .email(userDetails.getUsername())
                .build());

        return ResponseEntity.ok().body(Map.of("orderUid", orderUid));
    }
}
