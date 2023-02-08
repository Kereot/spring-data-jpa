package com.gb.services;

import com.gb.dto.ProductDto;
import com.gb.entities.Product;
import com.gb.exceptions.ResourceNotFoundException;
import com.gb.repositories.ProductRepository;
import com.gb.repositories.specifications.ProductSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

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

    public List<Product> findAllForWs() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public Product update(ProductDto productDto) {
        Product product = productRepository.findById(productDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Can't update the product (not found in the DB) id: " + productDto.getId()));
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        return product;
    }
}
