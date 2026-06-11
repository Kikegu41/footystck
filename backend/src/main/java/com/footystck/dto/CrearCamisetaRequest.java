package com.footystck.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// DTO de entrada para crear o editar una camiseta (panel admin).
@Data
public class CrearCamisetaRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private Long equipoId;
    private String temporada;
    private String tipo;
    private String coleccion;

    @NotNull(message = "El precio es obligatorio")
    private Double precio;

    private Integer stock;
    private String imagenUrl;
    private String descripcion;
    private Boolean destacado;
}
