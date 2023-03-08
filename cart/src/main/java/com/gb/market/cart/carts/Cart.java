package com.gb.market.cart.carts;

import com.gb.market.api.dto.ProductInCartDto;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Getter
public class Cart {
    private final List<ProductInCartDto> itemsInCart;
    private BigDecimal totalPrice;

    public Cart() {
        this.itemsInCart = new ArrayList<>();
    }

//    public List<ProductInCartDto> getItemsInCart() {
//        return Collections.unmodifiableList(itemsInCart);
//    }

    public void addToCart(ProductInCartDto productInCartDto) {
        boolean hasMatch = false;
        for (ProductInCartDto inCartDto : itemsInCart) {
            if (Objects.equals(inCartDto.getId(), productInCartDto.getId())) {
                inCartDto.setQuantity(inCartDto.getQuantity() + 1);
                hasMatch = true;
                break;
            }
        }
        if (!hasMatch) {
            itemsInCart.add(productInCartDto);
        }
    }

    public void addToCart(Long id) {
        for (ProductInCartDto inCartDto : itemsInCart) {
            if (Objects.equals(inCartDto.getId(), id)) {
                inCartDto.setQuantity(inCartDto.getQuantity() + 1);
                break;
            }
        }
    }

    public void removeFromCart(Long id) {
        for (int i = 0; i < itemsInCart.size(); i++) {
            ProductInCartDto productInCartDto = itemsInCart.get(i);
            if (Objects.equals(productInCartDto.getId(), id)) {
                productInCartDto.setQuantity(productInCartDto.getQuantity() - 1);
                if (productInCartDto.getQuantity() == 0) {
                    itemsInCart.remove(i);
                }
                break;
            }
        }
    }

    public void removeAllItems() {
        itemsInCart.clear();
    }

    public void recalculate() {
        totalPrice = BigDecimal.ZERO;
        for (ProductInCartDto productInCartDto : itemsInCart) {
            for (int j = 0; j < productInCartDto.getQuantity(); j++) {
                totalPrice = totalPrice.add(productInCartDto.getPrice());
            }
        }
    }
}
