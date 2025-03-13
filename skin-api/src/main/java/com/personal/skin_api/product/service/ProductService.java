package com.personal.skin_api.product.service;

import com.personal.skin_api.product.service.dto.request.ProductRegisterServiceRequest;
import org.springframework.stereotype.Service;

public interface ProductService {
    void registerProduct(ProductRegisterServiceRequest request);
}
