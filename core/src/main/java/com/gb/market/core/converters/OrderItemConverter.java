package com.gb.market.core.converters;

import com.gb.market.api.dto.ProductInCartDto;
import com.gb.market.core.entities.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemConverter {
    public ProductInCartDto entityToDto(OrderItem orderItem) {
        return ProductInCartDto.builder()
                .id(orderItem.getProduct().getId())
                .name(orderItem.getProduct().getName())
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .build();
    }
}
