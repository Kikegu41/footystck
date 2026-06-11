package com.footystck.dto;

import lombok.Builder;
import lombok.Data;

// DTO de salida de una linea de pedido.
@Data
@Builder
public class PedidoItemDTO {
    private Long id;
    private Long camisetaId;
    private String camisetaNombre;
    private String imagenUrl;
    private Integer cantidad;
    private Double precioUnit;
    private String talla;
    private String nombreJugador;
    private String dorsal;
}
