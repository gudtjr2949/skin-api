package com.personal.skin_api.order.service;

import com.personal.skin_api.order.service.dto.request.OrderCreateServiceRequest;
import com.personal.skin_api.order.service.dto.response.OrderCreateResponse;
import com.personal.skin_api.order.service.dto.response.OrderDetailResponse;
import com.siot.IamportRestClient.exception.IamportResponseException;

import java.io.IOException;

public interface OrderService {
    OrderCreateResponse createOrder(OrderCreateServiceRequest request) throws IamportResponseException, IOException;
    OrderDetailResponse findOrder(Long orderId);
}
