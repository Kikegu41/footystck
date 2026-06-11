package com.footystck.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

// Convierte los errores en un JSON { "error": "..." } con codigo 400.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Errores que lanzamos a mano (login mal, no encontrado, etc.)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> error(RuntimeException e) {
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }

    // Errores de validacion de los formularios (@NotBlank, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validacion(MethodArgumentNotValidException e) {
        String mensaje = e.getFieldError() != null ? e.getFieldError().getDefaultMessage() : "Datos no validos";
        return ResponseEntity.badRequest().body(Map.of("error", mensaje));
    }
}


