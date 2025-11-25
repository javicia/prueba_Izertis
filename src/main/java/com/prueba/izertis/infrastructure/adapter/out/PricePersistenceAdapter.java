package com.prueba.izertis.infrastructure.adapter.out;


import com.prueba.izertis.domain.model.Price;
import com.prueba.izertis.domain.port.out.PriceRepositoryPort;
import com.prueba.izertis.infrastructure.adapter.out.repository.PriceR2dbcRepository;
import com.prueba.izertis.infrastructure.mapper.PriceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PricePersistenceAdapter implements PriceRepositoryPort {

    private final PriceR2dbcRepository repository;
    private final PriceMapper mapper;

    @Override
    public Mono<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Integer brandId) {
        return repository.findApplicable(applicationDate, productId, brandId)
                .map(mapper::toDomain);
    }
}
