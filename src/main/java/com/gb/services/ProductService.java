package com.gb.services;

import com.gb.entities.Product;
import com.gb.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Optional<List<Product>> findAllByPriceGreaterThan(Float price) {
        return productRepository.findAllByPriceGreaterThan(price);
    }

    public Optional<List<Product>> findAllByPriceLessThan(Float price) {
        return productRepository.findAllByPriceLessThan(price);
    }

    public Optional<List<Product>> findAllByPriceBetween(Float priceMin, Float priceMax) {
        return productRepository.findAllByPriceBetween(priceMin, priceMax);
    }
}
