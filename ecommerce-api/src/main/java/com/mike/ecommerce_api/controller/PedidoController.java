package com.mike.ecommerce_api.controller; // ⚠️ Recuerda tu paquete real

import com.mike.ecommerce_api.dto.PedidoRequest;
import com.mike.ecommerce_api.model.DetallePedido;
import com.mike.ecommerce_api.model.Pedido;
import com.mike.ecommerce_api.model.Producto;
import com.mike.ecommerce_api.model.Usuario;
import com.mike.ecommerce_api.repository.PedidoRepository;
import com.mike.ecommerce_api.repository.ProductoRepository;
import com.mike.ecommerce_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody PedidoRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailUsuario = auth.getName(); 
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario).orElseThrow();

        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (producto.getStock() < request.getCantidad()) {
            return ResponseEntity.badRequest().body("Error: No hay suficiente stock de " + producto.getName());
        }

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado("COMPLETADO");

        BigDecimal total = producto.getPrice().multiply(BigDecimal.valueOf(request.getCantidad()));
        pedido.setTotal(total);

        DetallePedido detalle = new DetallePedido();
        detalle.setPedido(pedido);
        detalle.setProducto(producto);
        detalle.setCantidad(request.getCantidad());
        detalle.setPrecioUnitario(producto.getPrice());

        pedido.setDetalles(List.of(detalle));

        producto.setStock(producto.getStock() - request.getCantidad());
        
        productoRepository.save(producto);
        pedidoRepository.save(pedido);

        return ResponseEntity.ok("Compra realizada con éxito! Total a pagar: " + total + "€");
    }
}