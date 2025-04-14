package com.personal.skin_api.order.service;

import com.personal.skin_api.order.service.dto.request.*;
import com.personal.skin_api.order.service.dto.response.OrderCreateTableResponse;
import com.personal.skin_api.order.service.dto.response.OrderDetailResponse;
import com.personal.skin_api.order.service.dto.response.OrderListResponse;
import com.personal.skin_api.order.service.dto.response.WritableReviewOrderListResponse;

public interface OrderService {
    OrderCreateTableResponse createOrderTable(OrderCreateTableServiceRequest request);
    String createBeforePaidOrder(OrderCreateBeforePaidServiceRequest request);
    OrderDetailResponse findOrder(OrderDetailServiceRequest request);
    OrderListResponse findMyOrderList(OrderListServiceRequest request);
    WritableReviewOrderListResponse findWritableReviewOrderList(WritableReviewOrderServiceRequest request);
    void changeOrderStatusToPaid(String orderUid);
}
