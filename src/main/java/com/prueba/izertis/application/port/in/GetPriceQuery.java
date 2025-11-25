package com.prueba.izertis.application.port.in;

import java.time.LocalDateTime;

public record GetPriceQuery(LocalDateTime applicationDate, Long productId, Integer
                            brandId) { }
