package com.gb.market.cart.services;

import com.gb.market.cart.converters.CartItemConverter;
import com.gb.market.cart.carts.Cart;
import com.gb.market.api.dto.ProductDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private Cart tempCart;
    private final CartItemConverter cartItemConverter;

    @PostConstruct
    public void init() {
        tempCart = new Cart();
    }

    public Cart getCurrentCart() {
        return tempCart;
    }

    public void removeItem(Long id) {
        tempCart.removeFromCart(id);
        tempCart.recalculate();
    }

    public void removeAllItems() {
        tempCart.removeAllItems();
        tempCart.recalculate();
    }

    public void addToCart(ProductDto productDto) {
        tempCart.addToCart(cartItemConverter.productDtoToProductInCartDto(productDto, 1));
        tempCart.recalculate();
    }

    public void increaseQuantity(Long id) {
        tempCart.addToCart(id);
        tempCart.recalculate();
    }
}
