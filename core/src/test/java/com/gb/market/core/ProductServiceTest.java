package com.gb.market.core;

import com.gb.market.api.dto.ProductDto;
import com.gb.market.core.entities.Product;
import com.gb.market.core.repositories.ProductRepository;
import com.gb.market.core.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductServiceTest {
    @Autowired
    ProductService productService;

    @MockBean
    ProductRepository productRepository;

    @Test
    public void findProductsTest() {
        Product productOne = new Product(1L, "One", 10f, null, null);
        Product productTwo = new Product(2L, "Two", 20f, null, null);
        Mockito.doReturn(Optional.of(productOne)).when(productRepository).findById(1L);
        Product product = productService.findById(1L).get();
        Assertions.assertEquals(10f, product.getPrice());

        List<Product> productList = new ArrayList<>();
        productList.add(productOne);
        productList.add(productTwo);
        Mockito.doReturn(productList).when(productRepository).findAll();
        Assertions.assertEquals("Two", productService.findAllForWs().get(1).getName());

        ProductDto productDto = new ProductDto(1L, "Three", 30f);
        Assertions.assertEquals(30f, productService.update(productDto).getPrice());
    }
}
