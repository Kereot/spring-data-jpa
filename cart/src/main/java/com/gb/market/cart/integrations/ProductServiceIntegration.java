package com.gb.market.cart.integrations;

import com.gb.market.api.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

// actually, not needed
@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {
    private final RestTemplate restTemplate;

    public Optional<ProductDto> getProductById(Long id) {
        return Optional.ofNullable(restTemplate.getForObject("http://localhost:8180/market-core/api/v1/products/" + id, ProductDto.class));
    }
}
