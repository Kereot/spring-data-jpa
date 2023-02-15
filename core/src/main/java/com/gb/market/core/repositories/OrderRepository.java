package com.gb.market.core.repositories;

import com.gb.market.core.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUsername(String username);
}
