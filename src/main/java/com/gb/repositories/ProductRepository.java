package com.gb.repositories;

import com.gb.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Optional<Product> findByName(String name);

    Optional<List<Product>> findAllByPriceGreaterThan(Float price);

    Optional<List<Product>> findAllByPriceLessThan(Float price);

    Optional<List<Product>> findAllByPriceBetween(Float priceMin, Float priceMax);
}
