package com.ecommerce.order.repositories;

import com.ecommerce.order.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public Optional<Order> findByOrderId(Long id);
}
