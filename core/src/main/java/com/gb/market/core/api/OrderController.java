package com.gb.market.core.api;

import com.gb.market.api.dto.OrderDto;
import com.gb.market.api.dto.ProductDto;
import com.gb.market.core.converters.OrderConverter;
import com.gb.market.core.entities.OrderItem;
import com.gb.market.core.entities.User;
import com.gb.market.core.integrations.CartServiceIntegration;
import com.gb.market.core.services.OrderService;
import com.gb.market.core.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/products/orders")
@RequiredArgsConstructor
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;
    private final OrderConverter orderConverter;
    private final CartServiceIntegration cartServiceIntegration;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(Principal principal){
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        orderService.createOrder(user);
    }

    @GetMapping
    public List<OrderDto> getCurrentUserOrders(Principal principal) {
        return orderService.findOrdersByUsername(principal.getName()).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        List<OrderItem> items = orderService.findOrderById(id).getItems();
        for (OrderItem item : items) {
            ProductDto productDto = new ProductDto();
            productDto.setId(item.getProduct().getId());
            productDto.setName(item.getProduct().getName());
            productDto.setPrice(item.getPrice());
            for (int j = item.getQuantity(); j > 0; j--) {
                cartServiceIntegration.addToCart(productDto);
            }
        }
        orderService.deleteOrder(id);
    }
}