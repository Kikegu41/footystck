package com.footystck.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

// DTO de entrada para crear un pedido (el carrito + el id del usuario).
@Data
public class CrearPedidoRequest {

    private Long usuarioId;

    @NotEmpty(message = "El carrito esta vacio")
    private List<ItemRequest> items;

    // Codigo promocional (opcional) y datos de envio
    private String codigoPromo;
    private DatosEnvio datosEnvio;

    // Cada linea del carrito
    @Data
    public static class ItemRequest {
        private Long camisetaId;
        private Integer cantidad;
        private String talla;
        private String nombreJugador;
        private String dorsal;
    }

    // Direccion de envio
    @Data
    public static class DatosEnvio {
        private String nombre;
        private String apellidos;
        private String pais;
        private String region;
        private String ciudad;
        private String cp;
        private String direccion;
        private String telefono;
    }
}
