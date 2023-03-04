package com.gb.market.cart.controllers;

import com.gb.market.api.dto.CartDto;
import com.gb.market.cart.converters.CartConverter;
import com.gb.market.cart.services.CartService;
import com.gb.market.api.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

//    @GetMapping("/cart")
//    public Page<ProductInCartDto> loadCart() {
//        return new PageImpl<>(cartService.getCurrentCart().getItemsInCart(),
//                PageRequest.of(0, 10),
//                cartService.getCurrentCart().getItemsInCart().size());
//    }

    @GetMapping("/cart")
    public CartDto loadCart(@RequestHeader (name = "username", required = false) String username) {
        return cartConverter.entityToDto(cartService.getCurrentCart(username));
    }

    @PostMapping("/cart")
    public void addToCart(@RequestHeader (name = "username", required = false) String username, @RequestBody ProductDto productDto){
        cartService.addToCart(productDto, username);
    }

    @PostMapping("/cart/{id}")
    public void increaseQuantity(@RequestHeader (name = "username", required = false) String username, @PathVariable Long id) {
        cartService.increaseQuantity(id, username);
    }

    @DeleteMapping("/cart/{id}")
    public void deleteProductFromCart(@RequestHeader (name = "username", required = false) String username, @PathVariable Long id){
        cartService.removeItem(id, username);
    }

    @DeleteMapping("/cart")
    public void clearCart(@RequestHeader (name = "username", required = false) String username) {
        cartService.removeAllItems(username);
    }

    @GetMapping("/cart/total_price")
    public Float showTotalPrice(@RequestHeader (name = "username", required = false) String username) {
        return cartService.getCurrentCart(username).getTotalPrice();
    }
}
