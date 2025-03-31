package com.personal.skin_api.payment.service;

import com.personal.skin_api.payment.service.dto.request.PaymentCreateServiceRequest;
import com.personal.skin_api.payment.service.dto.request.PaymentFindServiceRequest;
import com.personal.skin_api.payment.service.dto.response.PaymentDetailResponse;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {
    void createPayment(PaymentCreateServiceRequest request);
    PaymentDetailResponse findPayment(PaymentFindServiceRequest request);
}