package com.footystck.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "equipos")
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del equipo es obligatorio")
    private String nombre;

    private String escudoUrl;

    // Muchos equipos pertenecen a una liga
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "liga_id")
    private Liga liga;

    // true = equipo grande añadido a mano (categoria "Otros")
    private Boolean esOtros = false;

    // id del equipo en API-Football
    private Integer codigoApi;
}
