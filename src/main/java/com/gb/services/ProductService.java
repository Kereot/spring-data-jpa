package com.gb.services;

import com.gb.entities.Product;
import com.gb.repositories.ProductRepository;
import com.gb.repositories.specifications.ProductSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> find(Float minPrice, Float maxPrice, String name, Integer page) {
        Specification<Product> spec = Specification.where(null);
        if (minPrice != null) {
            spec = spec.and(ProductSpecifications.priceGreaterOrEqualThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductSpecifications.priceLessOrEqualThan(maxPrice));
        }
        if (name != null) {
            spec = spec.and(ProductSpecifications.nameLike(name));
        }
        return productRepository.findAll(spec, PageRequest.of(page - 1, 10));
    }

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
