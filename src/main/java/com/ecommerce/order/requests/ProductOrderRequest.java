package com.ecommerce.order.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class ProductOrderRequest {
    @NotNull
    private String productId;
    @NotNull
    @Min(0)
    private Integer quantity;
}
