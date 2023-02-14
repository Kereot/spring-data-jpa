package com.gb.market.cart.converters;

import com.gb.market.api.dto.ProductDto;
import com.gb.market.api.dto.ProductInCartDto;
import org.springframework.stereotype.Component;

@Component
public class CartItemConverter {
    public ProductInCartDto productDtoToProductInCartDto(ProductDto productDto, int quantity) {
        return new ProductInCartDto(productDto.getId(), productDto.getName(), productDto.getPrice(), quantity);
    }
}
