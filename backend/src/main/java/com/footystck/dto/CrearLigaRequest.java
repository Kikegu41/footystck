package com.footystck.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// DTO de entrada para crear o editar una liga.
@Data
public class CrearLigaRequest {

    @NotBlank(message = "El nombre de la liga es obligatorio")
    private String nombre;

    private String pais;
    private Integer division;
    private Integer codigoApi;
}
