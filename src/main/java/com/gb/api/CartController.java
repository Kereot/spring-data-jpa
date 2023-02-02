package com.gb.api;

import com.gb.dto.ProductDto;
import com.gb.dto.ProductInCartDto;
import com.gb.services.CartService;
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

    @GetMapping("/cart")
    public Page<ProductInCartDto> loadCart() {
        return new PageImpl<>(cartService.getCurrentCart().getItemsInCart(),
                PageRequest.of(0, 10),
                cartService.getCurrentCart().getItemsInCart().size());
    }

    @PostMapping("/cart")
    public void addToCart(@RequestBody ProductDto productDto){
        cartService.addToCart(productDto);
    }

    @PostMapping("/cart/{id}")
    public void increaseQuantity(@PathVariable Long id) {
        cartService.increaseQuantity(id);
    }

    @DeleteMapping("/cart/{id}")
    public void deleteProductFromCart(@PathVariable Long id){
        cartService.removeItem(id);
    }

    @DeleteMapping("/cart")
    public void clearCart() {
        cartService.removeAllItems();
    }

    @GetMapping("/cart/total_price")
    public Float showTotalPrice() {
        return cartService.getCurrentCart().getTotalPrice();
    }
}
