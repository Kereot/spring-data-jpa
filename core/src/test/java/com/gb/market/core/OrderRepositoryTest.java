package com.gb.market.core;

import com.gb.market.core.entities.Order;
import com.gb.market.core.entities.OrderItem;
import com.gb.market.core.entities.Product;
import com.gb.market.core.repositories.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
public class OrderRepositoryTest {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void orderRepositoryTest() {
        Product product = new Product();
        product.setName("TestProduct");
        product.setPrice(100f);

        entityManager.persist(product);

        Order order = new Order();
        order.setUsername("TestUser");
        order.setTotalPrice(100f);

        entityManager.persist(order);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(1);
        orderItem.setPrice(100f);

        entityManager.persist(orderItem);
        entityManager.flush();

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        order.setItems(orderItems);

        List<Order> testList = orderRepository.findAll();

        Assertions.assertEquals(1, testList.size());
        Assertions.assertEquals(100f, testList.get(0).getTotalPrice());
        Assertions.assertEquals("TestProduct", testList.get(0)
                .getItems().get(0)
                .getProduct()
                .getName());
    }
}
