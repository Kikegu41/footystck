package com.footystck.service;

import com.footystck.dto.CrearPedidoRequest;
import com.footystck.dto.DTOMapper;
import com.footystck.dto.PedidoDTO;
import com.footystck.model.*;
import com.footystck.repository.CamisetaRepository;
import com.footystck.repository.PedidoRepository;
import com.footystck.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Logica de pedidos (incluye el checkout con su total).
@Service
public class PedidoService {

    // Recargo en euros por personalizar una camiseta (nombre o dorsal)
    private static final double EXTRA_PERSONALIZACION = 2;

    private final PedidoRepository pedidoRepository;
    private final CamisetaRepository camisetaRepository;
    private final UsuarioRepository usuarioRepository;

    public PedidoService(PedidoRepository pedidoRepository, CamisetaRepository camisetaRepository, UsuarioRepository usuarioRepository) {
        this.pedidoRepository = pedidoRepository;
        this.camisetaRepository = camisetaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Crea el pedido: calcula el total en el servidor y resta el stock.
    @Transactional
    public Map<String, Object> crear(CrearPedidoRequest req) {
        Usuario usuario = usuarioRepository.findById(req.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no valido"));

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setEstado("pagado"); // pago simulado

        List<PedidoItem> items = new ArrayList<>();
        double subtotal = 0;

        for (CrearPedidoRequest.ItemRequest it : req.getItems()) {
            Camiseta c = camisetaRepository.findById(it.getCamisetaId())
                    .orElseThrow(() -> new RuntimeException("La camiseta no existe"));

            int cantidad = it.getCantidad() != null ? it.getCantidad() : 1;
            double precio = (c.getPrecio() != null ? c.getPrecio() : 0) + (estaPersonalizada(it) ? EXTRA_PERSONALIZACION : 0);
            subtotal += precio * cantidad;

            items.add(crearLinea(pedido, c, it, cantidad, precio));
            c.setStock(Math.max((c.getStock() != null ? c.getStock() : 0) - cantidad, 0)); // restar stock
            c.setVentas((c.getVentas() != null ? c.getVentas() : 0) + cantidad);            // sumar ventas (popularidad)
        }

        // Descuento por codigo y total (envio gratis)
        double descuento = redondear(subtotal * porcentajePromo(req.getCodigoPromo()) / 100.0);
        double total = redondear(subtotal - descuento);

        pedido.setItems(items);
        pedido.setCodigoPromo(req.getCodigoPromo());
        pedido.setDescuento(descuento);
        pedido.setEnvio(0.0);
        pedido.setTotal(total);
        aplicarDatosEnvio(pedido, req.getDatosEnvio());

        Pedido guardado = pedidoRepository.save(pedido);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("id", guardado.getId());
        respuesta.put("total", total);
        respuesta.put("descuento", descuento);
        return respuesta;
    }

    // Porcentaje de descuento segun el codigo (0 si no es valido)
    private int porcentajePromo(String codigo) {
        if (codigo == null) return 0;
        switch (codigo.trim().toUpperCase()) {
            case "FOOTY5":  return 5;
            case "FOOTY10": return 10;
            case "FOOTY15": return 15;
            case "FOOTY20": return 20;
            // Codigos que se ganan en el minijuego del penalti
            case "GOL5":    return 5;
            case "GOL10":   return 10;
            case "GOL15":   return 15;
            default:        return 0;
        }
    }

    // Copia los datos de envio al pedido
    private void aplicarDatosEnvio(Pedido pedido, CrearPedidoRequest.DatosEnvio d) {
        if (d == null) return;
        pedido.setEnvioNombre(d.getNombre());
        pedido.setEnvioApellidos(d.getApellidos());
        pedido.setEnvioPais(d.getPais());
        pedido.setEnvioRegion(d.getRegion());
        pedido.setEnvioCiudad(d.getCiudad());
        pedido.setEnvioCp(d.getCp());
        pedido.setEnvioDireccion(d.getDireccion());
        pedido.setEnvioTelefono(d.getTelefono());
    }

    // Redondea a 2 decimales (euros)
    private double redondear(double n) {
        return Math.round(n * 100) / 100.0;
    }

    // Lista los pedidos de un usuario.
    public List<PedidoDTO> misPedidos(Long usuarioId) {
        return pedidoRepository.findByUsuarioIdOrderByFechaDesc(usuarioId).stream()
                .map(DTOMapper::toPedidoDTO).collect(Collectors.toList());
    }

    // Lista todos los pedidos (para el admin).
    public List<PedidoDTO> todos() {
        return pedidoRepository.findAll().stream().map(DTOMapper::toPedidoDTO).collect(Collectors.toList());
    }

    // Devuelve un pedido por su id.
    public PedidoDTO obtener(Long id) {
        Pedido p = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        return DTOMapper.toPedidoDTO(p);
    }

    // Crea una linea del pedido con su personalizacion.
    private PedidoItem crearLinea(Pedido pedido, Camiseta c, CrearPedidoRequest.ItemRequest it, int cantidad, double precio) {
        PedidoItem item = new PedidoItem();
        item.setPedido(pedido);
        item.setCamiseta(c);
        item.setCantidad(cantidad);
        item.setPrecioUnit(precio);
        item.setTalla(it.getTalla());
        item.setNombreJugador(it.getNombreJugador());
        item.setDorsal(it.getDorsal());
        return item;
    }

    // ¿La linea lleva nombre o dorsal?
    private boolean estaPersonalizada(CrearPedidoRequest.ItemRequest it) {
        return (it.getNombreJugador() != null && !it.getNombreJugador().isBlank())
                || (it.getDorsal() != null && !it.getDorsal().isBlank());
    }
}
