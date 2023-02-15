package com.gb.market.core.converters;

import com.gb.market.api.dto.OrderDto;
import com.gb.market.core.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConverter {
    private final OrderItemConverter orderItemConverter;

    public Order dtoToEntity(OrderDto orderDto) {
        throw new UnsupportedOperationException();
    }

    public OrderDto entityToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setTotalPrice(order.getTotalPrice());
        orderDto.setUsername(order.getUsername());
        orderDto.setItems(order.getItems().stream().map(orderItemConverter::entityToDto).collect(Collectors.toList()));
        return orderDto;
    }
}
