package com.prueba.izertis.application.port.in;

import com.prueba.izertis.application.dto.PriceDto;
import reactor.core.publisher.Mono;

public interface GetPriceUseCase {
    Mono<PriceDto> getPrice(GetPriceQuery query);
}
