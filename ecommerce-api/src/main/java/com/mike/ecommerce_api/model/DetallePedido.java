package com.mike.ecommerce_api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "order_details")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;

    private BigDecimal precioUnitario;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Producto producto;
}