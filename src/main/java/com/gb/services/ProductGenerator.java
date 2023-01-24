package com.gb.services;

import com.gb.entities.Product;
import com.gb.entities.User;
import com.gb.repositories.ProductRepository;
import com.gb.repositories.UserRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProductGenerator {
    private static final Faker faker = new Faker();

    @Autowired
    private ProductRepository productRepository;
//    @Autowired
//    private UserRepository userRepository;
//    Was used for testing BD

    @EventListener(ApplicationReadyEvent.class)
    public void generateProductsOnStartup() {
        for (int i = 0; i < 20; i++) {
            productRepository.save(generate());
        }
    }
//    Was used for testing BD
//    @EventListener(ApplicationReadyEvent.class)
//    public void fillDamnDatabase() {
//        User user = new User();
//        user.setUsername("user1");
//        user.setPassword("$2a$10$9Feu10NvX5ee9.LIzEWyMOWbShX9ScOLxdBwqnSnjLKRtPO.adR5e");
//        user.setEmail("user1@gmail.com");
//        userRepository.save(user);
//    }

    public static Product generate() {
        Product product = new Product();
        product.setName(faker.beer().name());
        product.setPrice((float) faker.number().randomDouble(2, 5, 30));

        return product;
    }
}
