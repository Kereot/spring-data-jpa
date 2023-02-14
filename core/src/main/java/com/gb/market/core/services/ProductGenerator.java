package com.gb.market.core.services;

import com.gb.market.core.aspect.Timer;
import com.gb.market.core.entities.Product;
import com.gb.market.core.repositories.ProductRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Timer
@Component
@RequiredArgsConstructor
public class ProductGenerator {
    private static final Faker faker = new Faker();

    private final ProductRepository productRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void generateProductsOnStartup() {
        for (int i = 0; i < 20; i++) {
            productRepository.save(generate());
        }
    }
    public static Product generate() {
        Product product = new Product();
        product.setName(faker.beer().name());
        product.setPrice((float) faker.number().randomDouble(2, 5, 30));

        return product;
    }
}
