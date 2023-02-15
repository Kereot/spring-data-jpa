package com.gb.market.core.services;

import com.gb.market.api.dto.CartDto;
import com.gb.market.api.exceptions.ResourceNotFoundException;
import com.gb.market.core.entities.Order;
import com.gb.market.core.entities.OrderItem;
import com.gb.market.core.integrations.CartServiceIntegration;
import com.gb.market.core.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository ordersRepository;
    private final CartServiceIntegration cartServiceIntegration;
    private final ProductService productsService;

    @Transactional
    public void createOrder(String username) {
        CartDto currentCart = cartServiceIntegration.getCart();
        Order order = new Order();
        order.setUsername(username);
        order.setTotalPrice(currentCart.getTotalPrice());
        List<OrderItem> items = currentCart.getProducts().stream()
                .map(o -> {
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setQuantity(o.getQuantity());
                    item.setPrice(o.getPrice());
                    item.setProduct(productsService.findById(o.getId()).orElseThrow(() -> new ResourceNotFoundException("Product not found")));
                    return item;
                }).collect(Collectors.toList());
        order.setItems(items);
        ordersRepository.save(order);
        cartServiceIntegration.clearCart();
    }

    public List<Order> findOrdersByUsername(String username) {
        return ordersRepository.findAllByUsername(username);
    }

    public Order findOrderById(Long id) {
        return ordersRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("This order doesn't exist"));
    }

    public void deleteOrder(Long id) {
        ordersRepository.deleteById(id);
    }
}