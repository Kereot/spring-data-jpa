package com.gb.market.core.integrations;

import com.gb.market.api.dto.CartDto;
import com.gb.market.api.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    private final RestTemplate restTemplate;

    public CartDto getCart() {
        return restTemplate.getForObject("http://localhost:8190/market-carts/api/v1/products/cart" , CartDto.class);
    }

    public void addToCart(ProductDto productDto) {
        restTemplate.postForObject("http://localhost:8190/market-carts/api/v1/products/cart", productDto, ProductDto.class);
    }

    public void clearCart(){
        restTemplate.delete("http://localhost:8190/market-carts/api/v1/products/cart");
    }
}
