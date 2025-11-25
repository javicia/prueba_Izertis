package com.prueba.izertis.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceDto(Integer brandId,
                       Long productId,
                       Integer priceList,
                       LocalDateTime startDate,
                       LocalDateTime endDate,
                       BigDecimal price,
                       String currency) {
}
