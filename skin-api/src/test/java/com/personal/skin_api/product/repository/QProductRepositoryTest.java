package com.personal.skin_api.product.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class QProductRepositoryTest {
    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    void beforeEach() {
        queryFactory = new JPAQueryFactory(em);
    }

    @Test
    void 제품_리스트_첫_장을_조회한다() {
        // given

        // when

        // then
    }

    @Test
    void 제품_리스트_첫_장_이후를_조회한다() {
        // given

        // when

        // then
    }

}
