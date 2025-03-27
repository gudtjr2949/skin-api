package com.personal.skin_api.order.service;

import com.personal.skin_api.order.service.dto.request.OrderCompleteServiceRequest;
import com.personal.skin_api.order.service.dto.request.OrderCreateBeforePaidServiceRequest;
import com.personal.skin_api.order.service.dto.request.OrderCreateTableServiceRequest;
import com.personal.skin_api.order.service.dto.request.OrderDetailRequest;
import com.personal.skin_api.order.service.dto.response.OrderCreateTableResponse;
import com.personal.skin_api.order.service.dto.response.OrderDetailResponse;
import com.siot.IamportRestClient.exception.IamportResponseException;
import org.springframework.core.io.Resource;

import java.io.IOException;

public interface OrderService {
    OrderCreateTableResponse createOrderTable(OrderCreateTableServiceRequest request);
    String createBeforePaidOrder(OrderCreateBeforePaidServiceRequest request);
    OrderDetailResponse findOrder(OrderDetailRequest request);
}
