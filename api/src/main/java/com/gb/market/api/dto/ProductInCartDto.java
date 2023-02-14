package com.gb.market.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInCartDto {
    private Long id;

    private String name;

    private Float price;

    private Integer quantity;
}
