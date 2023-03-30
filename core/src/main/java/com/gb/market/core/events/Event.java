package com.gb.market.core.events;

import com.gb.market.api.dto.ProductDto;
import org.springframework.context.ApplicationEvent;

public class Event extends ApplicationEvent {
    private final ProductDto productDto;

    public Event(Object source, ProductDto productDto) {
        super(source);
        this.productDto = productDto;
    }

    @Override
    public String toString() {
        return String.format("This product is subject to creation:%n- name: %s%n- price: %s", productDto.getName(), productDto.getPrice());
    }
}
