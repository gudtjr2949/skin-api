package com.personal.skin_api.product.service;

import com.personal.skin_api.product.service.dto.request.*;
import com.personal.skin_api.product.service.dto.response.ProductDetailResponse;
import com.personal.skin_api.product.service.dto.response.ProductListResponse;
import org.springframework.stereotype.Service;

public interface ProductService {
    Long registerProduct(ProductRegisterServiceRequest request);
    ProductListResponse findProducts(ProductFindListServiceRequest request);
    ProductListResponse findProductsWithOffset(ProductFindListServiceRequest request);
    ProductListResponse findMyProducts(ProductFindMyListServiceRequest request);
    ProductDetailResponse findProductDetail(Long productId);
    void modifyProduct(ProductModifyServiceRequest request);
    void deleteProduct(ProductDeleteServiceRequest request);
}
