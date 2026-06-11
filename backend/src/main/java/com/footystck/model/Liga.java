package com.footystck.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "ligas")
public class Liga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la liga es obligatorio")
    private String nombre;

    private String pais;

    private Integer division;

    // id de la liga en API-Football
    private Integer codigoApi;

    // Una liga tiene muchos equipos. Lo ignoramos en el JSON para evitar bucles;
    // el menu de categorias se construye con un DTO aparte.
    @JsonIgnore
    @OneToMany(mappedBy = "liga", fetch = FetchType.EAGER)
    private List<Equipo> equipos;
}
