package com.footystck.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pedido_items")
public class PedidoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // A que pedido pertenece (lo ignoramos en el JSON para evitar bucles)
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    // Que camiseta es
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "camiseta_id")
    private Camiseta camiseta;

    private Integer cantidad = 1;

    private Double precioUnit;

    private String talla;

    // Personalizacion (opcional)
    private String nombreJugador;

    private String dorsal;
}
