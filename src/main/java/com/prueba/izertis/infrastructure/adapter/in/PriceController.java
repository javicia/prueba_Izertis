package com.prueba.izertis.infrastructure.adapter.in;

import com.prueba.izertis.application.port.in.GetPriceUseCase;
import com.prueba.izertis.application.port.in.GetPriceQuery;
import com.prueba.izertis.infrastructure.mapper.PriceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/prices")
@RequiredArgsConstructor
public class PriceController {

    private final GetPriceUseCase getPriceUseCase;
    private final PriceMapper mapper;

    @GetMapping
    public Mono<PriceResponse> getPrice(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
                                        @RequestParam Long productId,
                                        @RequestParam Integer brandId) {
        GetPriceQuery query = new GetPriceQuery(applicationDate, productId, brandId);
        return getPriceUseCase.getPrice(query).map(mapper::toResponse);
    }
}
