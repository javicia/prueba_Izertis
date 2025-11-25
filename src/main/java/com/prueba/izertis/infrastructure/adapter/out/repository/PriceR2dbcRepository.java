package com.prueba.izertis.infrastructure.adapter.out.repository;

import com.prueba.izertis.infrastructure.adapter.out.entity.PriceEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface PriceR2dbcRepository extends ReactiveCrudRepository<PriceEntity, Long> {

    @Query("""
        SELECT *
        FROM prices
        WHERE brand_id = :brandId
          AND product_id = :productId
          AND start_date <= :applicationDate
          AND end_date   >= :applicationDate
        ORDER BY priority DESC, start_date DESC
        LIMIT 1
    """)

    Mono<PriceEntity> findApplicable(LocalDateTime applicationDate, Long productId, Integer brandId);

}
