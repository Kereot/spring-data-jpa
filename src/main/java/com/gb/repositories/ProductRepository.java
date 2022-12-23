package com.gb.repositories;

import com.gb.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String name);

    Optional<List<Product>> findAllByPriceGreaterThan(Float price);

    Optional<List<Product>> findAllByPriceLessThan(Float price);

    Optional<List<Product>> findAllByPriceBetween(Float priceMin, Float priceMax);
}
