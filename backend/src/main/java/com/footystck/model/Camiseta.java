package com.footystck.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "camisetas")
public class Camiseta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    // Muchas camisetas pueden ser del mismo equipo
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipo_id")
    private Equipo equipo;

    private String temporada;

    // local / visitante / tercera
    private String tipo = "local";

    // actual / retro
    private String coleccion = "actual";

    @NotNull(message = "El precio es obligatorio")
    private Double precio;

    private Integer stock = 0;

    private String imagenUrl;

    @Column(length = 1000)
    private String descripcion;

    private Boolean destacado = false;

    private Integer ventas = 0;   // unidades vendidas (para ordenar por popularidad)
}
