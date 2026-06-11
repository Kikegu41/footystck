package com.footystck.dto;

import lombok.Builder;
import lombok.Data;

// DTO de salida de un equipo (para el menu de categorias y la lista "Otros").
@Data
@Builder
public class EquipoDTO {
    private Long id;
    private String nombre;
    private String escudoUrl;
    private Long ligaId;
    private Boolean esOtros;
}
