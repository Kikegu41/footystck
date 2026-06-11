package com.footystck.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // El usuario que hizo el pedido
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private LocalDateTime fecha = LocalDateTime.now();

    private Double total = 0.0;

    // Desglose del cobro
    private String codigoPromo;
    private Double descuento = 0.0;
    private Double envio = 0.0;

    // Datos de envio
    private String envioNombre;
    private String envioApellidos;
    private String envioPais;
    private String envioRegion;
    private String envioCiudad;
    private String envioCp;
    private String envioDireccion;
    private String envioTelefono;

    // pendiente / pagado / enviado / cancelado
    private String estado = "pagado";

    // Un pedido tiene muchas lineas; al guardar el pedido se guardan tambien (cascade)
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PedidoItem> items;
}
