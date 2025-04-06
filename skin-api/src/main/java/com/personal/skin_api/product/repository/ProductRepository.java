package com.personal.skin_api.product.repository;

import com.personal.skin_api.product.repository.entity.Product;
import com.personal.skin_api.product.repository.entity.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByIdAndProductStatus(Long id, ProductStatus productStatus);
}
