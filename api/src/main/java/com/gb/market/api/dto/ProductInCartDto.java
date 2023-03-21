package com.gb.market.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInCartDto {
    private Long id;

    private String name;

    private BigDecimal price;

    private Integer quantity;
}
