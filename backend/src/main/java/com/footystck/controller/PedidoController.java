package com.footystck.controller;

import com.footystck.dto.CrearPedidoRequest;
import com.footystck.dto.PedidoDTO;
import com.footystck.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// Endpoints de pedidos.
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // Crea un pedido con el contenido del carrito (checkout).
    @PostMapping
    public Map<String, Object> crear(@Valid @RequestBody CrearPedidoRequest request) {
        return pedidoService.crear(request);
    }

    // Lista los pedidos de un usuario.
    @GetMapping("/mios")
    public List<PedidoDTO> mios(@RequestParam(name = "usuario_id") Long usuarioId) {
        return pedidoService.misPedidos(usuarioId);
    }

    // Lista todos los pedidos (para el admin).
    @GetMapping
    public List<PedidoDTO> todos() {
        return pedidoService.todos();
    }

    // Devuelve el detalle de un pedido.
    @GetMapping("/{id}")
    public PedidoDTO obtener(@PathVariable Long id) {
        return pedidoService.obtener(id);
    }
}
