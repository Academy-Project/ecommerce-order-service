package com.ecommerce.order.models;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product {
    private String productId;
    private String name;
    private String description;
    private Long price;
    private Integer stock;

    private LocalDate createdAt;
    private LocalDate lastModifiedAt;
    private LocalDate deletedAt = null;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastModifiedAt = LocalDate.now();
    }
}