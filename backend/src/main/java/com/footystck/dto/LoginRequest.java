package com.footystck.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// DTO de entrada para el login.
@Data
public class LoginRequest {

    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
