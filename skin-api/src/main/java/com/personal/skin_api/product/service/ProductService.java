package com.personal.skin_api.product.service;

import com.personal.skin_api.product.service.dto.request.ProductFindListServiceRequest;
import com.personal.skin_api.product.service.dto.request.ProductFindMyListServiceRequest;
import com.personal.skin_api.product.service.dto.request.ProductModifyServiceRequest;
import com.personal.skin_api.product.service.dto.request.ProductRegisterServiceRequest;
import com.personal.skin_api.product.service.dto.response.ProductDetailResponse;
import com.personal.skin_api.product.service.dto.response.ProductListResponse;
import org.springframework.stereotype.Service;

public interface ProductService {
    void registerProduct(ProductRegisterServiceRequest request);
    ProductListResponse findProducts(ProductFindListServiceRequest request);
    ProductListResponse findMyProducts(ProductFindMyListServiceRequest request);
    ProductDetailResponse findProductDetail(Long productId);
    void modifyProduct(ProductModifyServiceRequest request);
}
