package com.gb.market.cart.converters;

import com.gb.market.api.dto.CartDto;
import com.gb.market.cart.carts.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartConverter {
    private final CartItemConverter cartItemConverter;

    public CartDto entityToDto(Cart cart) {
        return new CartDto(cart.getItemsInCart(), cart.getTotalPrice());
    }
}
