package com.footystck.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

// DTO de salida de un pedido (con sus lineas y los datos del cliente).
@Data
@Builder
public class PedidoDTO {
    private Long id;
    private Long usuarioId;
    private String clienteNombre;
    private String clienteEmail;
    private LocalDateTime fecha;
    private Double total;
    private String codigoPromo;
    private Double descuento;
    private Double envio;
    private String estado;
    // Datos de envio
    private String envioNombre;
    private String envioApellidos;
    private String envioPais;
    private String envioRegion;
    private String envioCiudad;
    private String envioCp;
    private String envioDireccion;
    private String envioTelefono;
    private List<PedidoItemDTO> items;
}
