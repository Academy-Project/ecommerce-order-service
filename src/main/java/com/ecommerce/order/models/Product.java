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
    private String id_product;
    private String nama_product;
    private String deskripsi_product;
    private Integer harga_product;
    private Integer stock_product;
}