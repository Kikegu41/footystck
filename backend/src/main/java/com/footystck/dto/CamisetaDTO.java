package com.footystck.dto;

import lombok.Builder;
import lombok.Data;

// DTO de salida de una camiseta (con datos del equipo y liga ya "aplanados").
@Data
@Builder
public class CamisetaDTO {
    private Long id;
    private String nombre;
    private Long equipoId;
    private String temporada;
    private String tipo;
    private String coleccion;
    private Double precio;
    private Integer stock;
    private String imagenUrl;
    private String descripcion;
    private Boolean destacado;
    private Integer ventas;   // unidades vendidas (popularidad)
    // Datos del equipo y la liga (para mostrarlos directamente)
    private String equipoNombre;
    private String equipoEscudo;
    private Long ligaId;
    private String ligaNombre;
}
