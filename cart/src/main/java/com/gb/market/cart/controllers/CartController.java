package com.gb.market.cart.controllers;

import com.gb.market.api.dto.CartDto;
import com.gb.market.cart.converters.CartConverter;
import com.gb.market.cart.services.CartService;
import com.gb.market.api.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.gb.market.api.dto.StringResponse;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/cart/generate_uuid")
    public StringResponse generateUuid() {
        return new StringResponse(UUID.randomUUID().toString());
    }

//    @GetMapping("/cart")
//    public CartDto loadCart(@RequestHeader (name = "username", required = false) String username) {
//        return cartConverter.entityToDto(cartService.getCurrentCart(username));
//    }
    @GetMapping("/cart/{uuid}") //
    public CartDto loadCart(@PathVariable String uuid, @RequestHeader(name = "username", required = false) String username) {
        String targetUuid = cartService.getCartUuid(username, uuid);
        return cartConverter.entityToDto(cartService.getCurrentCart(targetUuid));
    }

//    @PostMapping("/cart")
//    public void addToCart(@RequestHeader (name = "username", required = false) String username, @RequestBody ProductDto productDto){
//        cartService.addToCart(productDto, username);
//    }
    @PostMapping("/cart/{uuid}")
    public void addToCart(@PathVariable String uuid, @RequestHeader(name = "username", required = false) String username, @RequestBody ProductDto productDto) {
        String targetUuid = cartService.getCartUuid(username, uuid);
        cartService.addToCart(productDto, targetUuid);
    }

//    @PostMapping("/cart/{id}")
//    public void increaseQuantity(@RequestHeader (name = "username", required = false) String username, @PathVariable Long id) {
//        cartService.increaseQuantity(id, username);
//    }
    @PostMapping("/cart/{uuid}/{id}")
    public void increaseQuantity(@PathVariable String uuid, @PathVariable Long id, @RequestHeader(name = "username", required = false) String username) {
        String targetUuid = cartService.getCartUuid(username, uuid);
        cartService.increaseQuantity(id, targetUuid);
    }

//    @DeleteMapping("/cart/{id}")
//    public void deleteProductFromCart(@RequestHeader (name = "username", required = false) String username, @PathVariable Long id){
//        cartService.removeItem(id, username);
//    }
    @DeleteMapping("/cart/{uuid}/{id}")
    public void deleteProductFromCart(@PathVariable String uuid, @PathVariable Long id, @RequestHeader(name = "username", required = false) String username) {
        String targetUuid = cartService.getCartUuid(username, uuid);
        cartService.removeItem(id, targetUuid);
    }

//    @DeleteMapping("/cart")
//    public void clearCart(@RequestHeader (name = "username", required = false) String username) {
//        cartService.removeAllItems(username);
//    }
    @DeleteMapping("/cart/{uuid}")
    public void clearCart(@PathVariable String uuid, @RequestHeader(name = "username", required = false) String username) {
        String targetUuid = cartService.getCartUuid(username, uuid);
        cartService.removeAllItems(targetUuid);
    }

//    @GetMapping("/cart/total_price")
//    public BigDecimal showTotalPrice(@RequestHeader (name = "username", required = false) String username) {
//        return cartService.getCurrentCart(username).getTotalPrice();
//    }
    @GetMapping("/cart/{uuid}/total_price")
    public BigDecimal showTotalPrice(@PathVariable String uuid, @RequestHeader (name = "username", required = false) String username) {
        String targetUuid = cartService.getCartUuid(username, uuid);
        return cartService.getCurrentCart(targetUuid).getTotalPrice();
    }

    @GetMapping("/cart/merge/{uuid}")
    public CartDto transferItemsInCart(@PathVariable String uuid, @RequestHeader (name = "username") String username) {
        return cartConverter.entityToDto(cartService.transferItemsInCart(uuid, username));
    }
}
