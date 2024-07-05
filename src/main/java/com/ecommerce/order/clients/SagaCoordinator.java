package com.ecommerce.order.clients;

import com.ecommerce.order.models.Order;
import com.ecommerce.order.models.Product;
import com.ecommerce.order.repositories.OrderRepository;
import com.ecommerce.order.responses.ResponseHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;

@Service
public class SagaCoordinator {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private OrderRepository orderRepository;

    private final String productServiceUrl = "http://localhost:8002/product";

    @Transactional
    public ResponseEntity<?> createOrder(Order order) {
        // Reserve Product Stock
        ResponseEntity<Product> productResponse = restTemplate.getForEntity(productServiceUrl + "/getById/" + order.getProductId(), Product.class);

        Product product = productResponse.getBody();

        if (product == null) {
            return ResponseHandler.errorResponse(Collections.singletonList("Product not available or insufficient stock"), HttpStatus.BAD_REQUEST);
        }
        if (product.getStock_product() < order.getQuantity()) {
            return ResponseHandler.errorResponse(Collections.singletonList("Product not available or insufficient stock"), HttpStatus.BAD_REQUEST);
        }
        Integer backupStock = product.getStock_product();

        // Update product stock
        product.setStock_product(product.getStock_product() - order.getQuantity());
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Product> requestEntity = new HttpEntity<>(product, headers);
        restTemplate.exchange(productServiceUrl + "/updateProduct/" + product.getId_product() + "salah", HttpMethod.PUT, requestEntity, Void.class);

        try {
            // Save order
            orderRepository.save(order);
        } catch (Exception e) {
            // Rollback Product Stock if Order creation fails
            product.setStock_product(backupStock);
            restTemplate.exchange(productServiceUrl + "/updateProduct/" + product.getId_product(), HttpMethod.PUT, requestEntity, Void.class);
            throw new RuntimeException("Order creation failed: " + e.getMessage());
        }

        return ResponseHandler.generateResponse("Order created successfully", order, HttpStatus.CREATED);
    }
}
