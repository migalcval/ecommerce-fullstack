package com.mike.ecommerce_api.dto;
import lombok.Data;

@Data
public class PedidoRequest {
    private Long productoId;
    private Integer cantidad;
}
