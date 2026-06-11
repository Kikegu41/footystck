package com.footystck.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// DTO de entrada para crear o editar un equipo (sobre todo los de "Otros").
@Data
public class CrearEquipoRequest {

    @NotBlank(message = "El nombre del equipo es obligatorio")
    private String nombre;

    private String escudoUrl;
    private Long ligaId;
    private Boolean esOtros;
}
