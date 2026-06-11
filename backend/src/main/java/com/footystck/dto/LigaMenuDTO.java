package com.footystck.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

// DTO del menu de categorias: una liga con sus equipos dentro.
@Data
@Builder
public class LigaMenuDTO {
    private Long id;
    private String nombre;
    private String pais;
    private Integer division;
    private List<EquipoDTO> equipos;
}
