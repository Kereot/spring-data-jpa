package com.gb.converters;

import com.gb.dto.ProductDto;
import com.gb.dto.ProductInCartDto;
import com.gb.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {
    public Product dtoToEntity(ProductDto productDto) {
        return new Product(productDto.getName(), productDto.getPrice());
    }

    public ProductDto entityToDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getPrice());
    }

    public ProductInCartDto productDtoToProductInCartDto(ProductDto productDto, int quantity) {
        return new ProductInCartDto(productDto.getId(), productDto.getName(), productDto.getPrice(), quantity);
    }
}
