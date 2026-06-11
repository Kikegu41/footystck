package com.footystck.controller;

import com.footystck.dto.LoginRequest;
import com.footystck.dto.RegistroRequest;
import com.footystck.dto.UsuarioDTO;
import com.footystck.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

// Endpoints de registro e inicio de sesion.
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Registra un usuario y lo devuelve.
    @PostMapping("/registro")
    public UsuarioDTO registrar(@Valid @RequestBody RegistroRequest request) {
        return authService.registrar(request);
    }

    // Inicia sesion y devuelve el usuario (id, nombre, rol).
    @PostMapping("/login")
    public UsuarioDTO login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
