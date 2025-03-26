package com.personal.skin_api.order.service;

import com.personal.skin_api.order.service.dto.request.OrderSaveServiceRequest;
import com.siot.IamportRestClient.exception.IamportResponseException;

import java.io.IOException;

public interface OrderService {
    void saveOrder(OrderSaveServiceRequest request) throws IamportResponseException, IOException;
}
