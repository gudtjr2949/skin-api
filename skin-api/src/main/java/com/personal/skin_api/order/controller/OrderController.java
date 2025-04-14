package com.personal.skin_api.order.controller;

import com.personal.skin_api.order.controller.dto.request.OrderListRequest;
import com.personal.skin_api.order.service.OrderService;
import com.personal.skin_api.order.service.dto.request.OrderCreateBeforePaidServiceRequest;
import com.personal.skin_api.order.service.dto.request.OrderCreateTableServiceRequest;
import com.personal.skin_api.order.service.dto.request.OrderDetailServiceRequest;

import com.personal.skin_api.order.service.dto.request.WritableReviewOrderServiceRequest;
import com.personal.skin_api.order.service.dto.response.OrderCreateTableResponse;
import com.personal.skin_api.order.service.dto.response.OrderDetailResponse;
import com.personal.skin_api.order.service.dto.response.OrderListResponse;
import com.personal.skin_api.order.service.dto.response.WritableReviewOrderListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/detail")
    public ResponseEntity<OrderDetailResponse> detailOrder(@RequestParam String orderUid,
                                                           @AuthenticationPrincipal UserDetails userDetails) {
        OrderDetailResponse response = orderService.findOrder(OrderDetailServiceRequest.builder()
                .email(userDetails.getUsername())
                .orderUid(orderUid)
                .build());

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/list")
    public ResponseEntity<OrderListResponse> findOrderList(@RequestBody OrderListRequest request,
                                                           @AuthenticationPrincipal UserDetails userDetails) {
        OrderListResponse response = orderService.findMyOrderList(request.toService(userDetails.getUsername()));

        return ResponseEntity.ok().body(response);
    }
    
    @GetMapping("/orders/writable-reviews")
    public ResponseEntity<WritableReviewOrderListResponse> findWritableReviewsOrder(@RequestParam Long orderId,
                                                           @AuthenticationPrincipal UserDetails userDetails) {
        WritableReviewOrderListResponse response = orderService.findWritableReviewOrderList(WritableReviewOrderServiceRequest.builder()
                .orderId(orderId)
                .email(userDetails.getUsername())
                .build());

        return ResponseEntity.ok().body(response);
    }
}
