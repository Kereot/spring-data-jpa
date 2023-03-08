package com.gb.market.cart.services;

import com.gb.market.api.dto.ProductInCartDto;
import com.gb.market.cart.converters.CartItemConverter;
import com.gb.market.cart.carts.Cart;
import com.gb.market.api.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {
//    private Map<String, Cart> tempCarts;
    private final RedisTemplate<String, Object> redisTemplate;
    private final CartItemConverter cartItemConverter;

//    @PostConstruct
//    public void init() {
//        tempCarts = new HashMap<>();
//        tempCarts.put(null, new Cart());
//    }
    @Value("${cart-service.cart-prefix}")
    private String cartPrefix;

//    public Cart getCurrentCart(String username) {
//        if (!tempCarts.containsKey(username)) {
//            tempCarts.put(username, new Cart());
//        }
//        return tempCarts.get(username);
//    }
    public Cart getCurrentCart(String uuid) {
        String targetUuid = cartPrefix + uuid;
        if (Boolean.FALSE.equals(redisTemplate.hasKey(targetUuid))) {
            redisTemplate.opsForValue().set(targetUuid, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(targetUuid);
    }

//    public void removeItem(Long id, String username) {
//        tempCarts.get(username).removeFromCart(id);
//        tempCarts.get(username).recalculate();
//    }
    public void removeItem(Long id, String uuid) {
        execute(uuid, cart -> cart.removeFromCart(id));
    }

//    public void removeAllItems(String username) {
//        tempCarts.get(username).removeAllItems();
//        tempCarts.get(username).recalculate();
//    }
    public void removeAllItems (String uuid) {
        execute(uuid, Cart::removeAllItems);
    }

//    public void addToCart(ProductDto productDto, String username) {
//        if (!tempCarts.containsKey(username)) {
//            tempCarts.put(username, new Cart());
//        }
//        tempCarts.get(username).addToCart(cartItemConverter.productDtoToProductInCartDto(productDto, 1));
//        tempCarts.get(username).recalculate();
//    }
    public void addToCart(ProductDto productDto, String uuid) {
        execute(uuid, cart -> cart.addToCart(cartItemConverter.productDtoToProductInCartDto(productDto, 1)));
    }

//    public void increaseQuantity(Long id, String username) {
//        tempCarts.get(username).addToCart(id);
//        tempCarts.get(username).recalculate();
//    }
    public void increaseQuantity(Long id, String uuid) {
        execute(uuid, cart -> cart.addToCart(id));
    }

    private void execute(String uuid, Consumer<Cart> operation) {
        Cart cart = getCurrentCart(uuid);
        operation.accept(cart);
        updateCart(uuid,cart);
    }

    private void updateCart(String uuid, Cart cart){
        cart.recalculate();
        redisTemplate.opsForValue().set(cartPrefix + uuid, cart);
    }

    public String getCartUuid(String username, String uuid) {
        if (username != null) {
            return username;
        }
        return uuid;
    }

    public Cart transferItemsInCart(String uuid, String username) {
        Cart guestCart = getCurrentCart(uuid);
        Cart userCart = getCurrentCart(username);
        if (guestCart.getItemsInCart().isEmpty()) {
            return userCart;
        }
        for (int i = 0; i < guestCart.getItemsInCart().size(); i++) {
            ProductInCartDto productInCartDto = guestCart.getItemsInCart().get(i);
            for (int j = productInCartDto.getQuantity(); j > 0; j--) {
                userCart.addToCart(productInCartDto);
            }
        }
        guestCart.removeAllItems();
        updateCart(uuid, guestCart);
        updateCart(username, userCart);
        return userCart;
    }
}
