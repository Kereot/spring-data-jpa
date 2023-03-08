package com.gb.market.core.integrations;

import com.gb.market.api.dto.CartDto;
import com.gb.market.api.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
//    private final RestTemplate restTemplate;
//
//    public CartDto getCart() {
//        return restTemplate.getForObject("http://localhost:8190/market-carts/api/v1/products/cart" , CartDto.class);
//    }
//
//    public void addToCart(ProductDto productDto) {
//        restTemplate.postForObject("http://localhost:8190/market-carts/api/v1/products/cart", productDto, ProductDto.class);
//    }
//
//    public void clearCart(){
//        restTemplate.delete("http://localhost:8190/market-carts/api/v1/products/cart");
//    }
    private final WebClient cartServiceWebClient;

    public CartDto getCart(String username) {
        return cartServiceWebClient.get()
                .uri("/api/v1/products/cart/0")
                .header("username", username)
                .retrieve()
                .bodyToMono(CartDto.class)
                .block();
    }

    public void addToCart(ProductDto productDto, String username) {
        cartServiceWebClient.post()
                .uri("/api/v1/products/cart/0")
                .contentType(MediaType.APPLICATION_JSON)
                .header("username", username)
                .body(BodyInserters.fromValue(productDto))
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .body(Mono.just(productDto), ProductDto.class)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void clearCart(String username) {
        cartServiceWebClient.delete()
                .uri("/api/v1/products/cart/0")
                .header("username", username)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

}
