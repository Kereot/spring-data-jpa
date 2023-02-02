package com.gb.services;

import com.gb.carts.Cart;
import com.gb.converters.ProductConverter;
import com.gb.dto.ProductDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private Cart tempCart;
    private final ProductConverter productConverter;

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
        tempCart.addToCart(productConverter.productDtoToProductInCartDto(productDto, 1));
        tempCart.recalculate();
    }

    public void increaseQuantity(Long id) {
        tempCart.addToCart(id);
        tempCart.recalculate();
    }
}
