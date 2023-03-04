package com.gb.market.cart.services;

import com.gb.market.cart.converters.CartItemConverter;
import com.gb.market.cart.carts.Cart;
import com.gb.market.api.dto.ProductDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartService {
    private Map<String, Cart> tempCarts;
    private final CartItemConverter cartItemConverter;

    @PostConstruct
    public void init() {
        tempCarts = new HashMap<>();
        tempCarts.put(null, new Cart());
    }

    public Cart getCurrentCart(String username) {
        if (!tempCarts.containsKey(username)) {
            tempCarts.put(username, new Cart());
        }
        return tempCarts.get(username);
    }

    public void removeItem(Long id, String username) {
        tempCarts.get(username).removeFromCart(id);
        tempCarts.get(username).recalculate();
    }

    public void removeAllItems(String username) {
        tempCarts.get(username).removeAllItems();
        tempCarts.get(username).recalculate();
    }

    public void addToCart(ProductDto productDto, String username) {
        if (!tempCarts.containsKey(username)) {
            tempCarts.put(username, new Cart());
        }
        tempCarts.get(username).addToCart(cartItemConverter.productDtoToProductInCartDto(productDto, 1));
        tempCarts.get(username).recalculate();
    }

    public void increaseQuantity(Long id, String username) {
        tempCarts.get(username).addToCart(id);
        tempCarts.get(username).recalculate();
    }
}
