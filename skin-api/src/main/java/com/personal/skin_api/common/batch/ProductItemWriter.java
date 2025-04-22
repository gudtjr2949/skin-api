package com.personal.skin_api.common.batch;

import com.personal.skin_api.product.repository.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class ProductItemWriter implements ItemWriter<Product> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void write(Chunk<? extends Product> chunk) {
        for (Product product : chunk) {
            entityManager.persist(product);
        }
        entityManager.flush();
        entityManager.clear();
    }
}