package com.ecommerce.order.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class OrderRequest {
    @NotNull
    List<ProductOrderRequest> products;
}
