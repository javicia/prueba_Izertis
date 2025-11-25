package com.prueba.izertis.application.service;

import com.prueba.izertis.application.dto.PriceDto;
import com.prueba.izertis.application.exception.PriceNotFoundException;
import com.prueba.izertis.application.exception.ServiceUnavailableException;
import com.prueba.izertis.application.port.in.GetPriceUseCase;
import com.prueba.izertis.application.port.in.GetPriceQuery;
import com.prueba.izertis.domain.port.out.PriceRepositoryPort;
import com.prueba.izertis.infrastructure.mapper.PriceMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;



@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements GetPriceUseCase {

    private final PriceRepositoryPort repositoryPort;
    private final PriceMapper mapper;

    @Override
    @CircuitBreaker(name = "priceService", fallbackMethod = "fallbackGetPrice")
    public Mono<PriceDto> getPrice(GetPriceQuery query) {

        if (query == null || query.applicationDate() == null || query.productId() == null || query.brandId() == null) {
            return Mono.error(new IllegalArgumentException("Required parameters: applicationDate, productId, brandId"));
        }

        return repositoryPort.findApplicablePrice(query.applicationDate(), query.productId(), query.brandId())
                .switchIfEmpty(Mono.error(
                        new PriceNotFoundException("There is no fee for the specified criteria.")))
                .map(mapper::toDto);
    }
    @SuppressWarnings("unused")
    private Mono<PriceDto> fallbackGetPrice(GetPriceQuery query, Throwable ex) {
        return Mono.error(new ServiceUnavailableException("Service temporarily unavailable"));
    }
}
