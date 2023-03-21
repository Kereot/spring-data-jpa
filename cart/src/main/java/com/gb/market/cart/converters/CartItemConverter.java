package com.gb.market.cart.converters;

import com.gb.market.api.dto.ProductDto;
import com.gb.market.api.dto.ProductInCartDto;
import org.springframework.stereotype.Component;

@Component
public class CartItemConverter {
    public ProductInCartDto productDtoToProductInCartDto(ProductDto productDto, int ProductQuantity) {
        return ProductInCartDto.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .quantity(ProductQuantity)
                .build();
    }
}
