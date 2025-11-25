package com.prueba.izertis.infrastructure.mapper;

import com.prueba.izertis.application.dto.PriceDto;
import com.prueba.izertis.domain.model.Price;
import com.prueba.izertis.infrastructure.adapter.in.PriceResponse;
import com.prueba.izertis.infrastructure.adapter.out.entity.PriceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    Price toDomain(PriceEntity entity);
    PriceDto toDto(Price domain);
    PriceResponse toResponse(PriceDto dto);
}
