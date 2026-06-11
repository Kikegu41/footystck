package com.footystck.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String password; // contraseña sin cifrar (login basico)
    private String rol = "cliente"; // 'cliente' o 'admin'
}
