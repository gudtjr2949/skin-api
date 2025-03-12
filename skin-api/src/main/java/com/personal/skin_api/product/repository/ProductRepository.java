package com.personal.skin_api.product.repository;

import com.personal.skin_api.product.repository.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

interface ProductRepository extends JpaRepository<Product, Long> {

}
