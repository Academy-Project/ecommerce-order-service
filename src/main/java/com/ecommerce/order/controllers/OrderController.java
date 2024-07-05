package com.ecommerce.order.controllers;

import com.ecommerce.order.clients.SagaCoordinator;
import com.ecommerce.order.models.Order;
import com.ecommerce.order.models.Product;
import com.ecommerce.order.repositories.OrderRepository;
import com.ecommerce.order.responses.ResponseHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private OrderRepository orderRepository;
    private SagaCoordinator sagaCoordinator;

    @GetMapping("")
    public @ResponseBody Iterable<Order> getList(@RequestParam(required = false) String search) {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody Optional<Order> getOne(@PathVariable(required = false) Long id) {
        return orderRepository.findByOrderId(id);
    }

    // create by request body
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody Order order) {
        return sagaCoordinator.createOrder(order);
    }

    // Delete - with query parameter
    @DeleteMapping("")
    public @ResponseBody boolean delete(@RequestParam Long id) {
        if (!orderRepository.existsById(id)) {
            return false;
        }

        orderRepository.deleteById(id);
        return true;
    }
}
