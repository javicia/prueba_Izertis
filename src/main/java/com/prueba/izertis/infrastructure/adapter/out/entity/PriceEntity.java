package com.prueba.izertis.infrastructure.adapter.out.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("prices")
@Data
public class PriceEntity {

    @Id
    private Long id;

    @Column("brand_id")
    Integer brandId;

    @Column("start_date")
    LocalDateTime startDate;

    @Column("end_date")
    LocalDateTime endDate;

    @Column("price_list")
    Integer priceList;

    @Column("product_id")
    Long productId;

    @Column("priority")
    Integer priority;

    @Column("price")
    BigDecimal price;

    @Column("currency")
    String currency;
}
