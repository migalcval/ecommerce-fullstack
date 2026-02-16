package com.mike.ecommerce_api.model;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "products")

public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String urlImage;

    private BigDecimal price;
    
    private Integer stock;

}
