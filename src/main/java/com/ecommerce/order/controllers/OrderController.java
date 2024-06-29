package com.ecommerce.order.controllers;

import com.ecommerce.order.models.Order;
import com.ecommerce.order.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

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
    public @ResponseBody boolean createV2(@RequestBody Order order) {
        // TODO: validation class
        orderRepository.save(order);
        // TODO: update product price & stock
        return true;
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
