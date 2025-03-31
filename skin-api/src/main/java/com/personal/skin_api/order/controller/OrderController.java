package com.personal.skin_api.order.controller;

import com.personal.skin_api.order.service.OrderService;
import com.personal.skin_api.order.service.dto.request.OrderCreateTableServiceRequest;
import com.personal.skin_api.order.service.dto.response.OrderCreateTableResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
