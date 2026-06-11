package com.footystck.dto;

import lombok.Builder;
import lombok.Data;

// DTO de salida del usuario: nunca incluye la contraseña.
@Data
@Builder
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String rol;
}
