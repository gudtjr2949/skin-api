package com.personal.skin_api.order.service;

import com.personal.skin_api.order.service.dto.request.OrderCreateBeforePaidServiceRequest;
import com.personal.skin_api.order.service.dto.request.OrderCreateTableServiceRequest;
import com.personal.skin_api.order.service.dto.request.OrderDetailServiceRequest;
import com.personal.skin_api.order.service.dto.request.OrderListServiceRequest;
import com.personal.skin_api.order.service.dto.response.OrderCreateTableResponse;
import com.personal.skin_api.order.service.dto.response.OrderDetailResponse;
import com.personal.skin_api.order.service.dto.response.OrderListResponse;

public interface OrderService {
    OrderCreateTableResponse createOrderTable(OrderCreateTableServiceRequest request);
    String createBeforePaidOrder(OrderCreateBeforePaidServiceRequest request);
    OrderDetailResponse findOrder(OrderDetailServiceRequest request);
    OrderListResponse findMyOrderList(OrderListServiceRequest request);
}
