package com.footystck.dto;

import com.footystck.model.*;

import java.util.List;
import java.util.stream.Collectors;

// Convierte las entidades (Model) en DTOs de salida.
public class DTOMapper {

    // Camiseta -> CamisetaDTO (aplana los datos del equipo y la liga).
    public static CamisetaDTO toCamisetaDTO(Camiseta c) {
        Equipo equipo = c.getEquipo();
        Liga liga = (equipo != null) ? equipo.getLiga() : null;
        return CamisetaDTO.builder()
                .id(c.getId()).nombre(c.getNombre())
                .equipoId(equipo != null ? equipo.getId() : null)
                .temporada(c.getTemporada()).tipo(c.getTipo()).coleccion(c.getColeccion())
                .precio(c.getPrecio()).stock(c.getStock()).imagenUrl(c.getImagenUrl())
                .descripcion(c.getDescripcion()).destacado(c.getDestacado())
                .ventas(c.getVentas())
                .equipoNombre(equipo != null ? equipo.getNombre() : null)
                .equipoEscudo(equipo != null ? equipo.getEscudoUrl() : null)
                .ligaId(liga != null ? liga.getId() : null)
                .ligaNombre(liga != null ? liga.getNombre() : null)
                .build();
    }

    // Lista de camisetas -> lista de DTOs.
    public static List<CamisetaDTO> toCamisetaDTOList(List<Camiseta> camisetas) {
        return camisetas.stream().map(DTOMapper::toCamisetaDTO).collect(Collectors.toList());
    }

    // Equipo -> EquipoDTO.
    public static EquipoDTO toEquipoDTO(Equipo e) {
        return EquipoDTO.builder()
                .id(e.getId()).nombre(e.getNombre()).escudoUrl(e.getEscudoUrl())
                .ligaId(e.getLiga() != null ? e.getLiga().getId() : null)
                .esOtros(e.getEsOtros())
                .build();
    }

    // Liga -> LigaMenuDTO (con sus equipos dentro).
    public static LigaMenuDTO toLigaMenuDTO(Liga l) {
        List<EquipoDTO> equipos = (l.getEquipos() == null) ? List.of()
                : l.getEquipos().stream().map(DTOMapper::toEquipoDTO).collect(Collectors.toList());
        return LigaMenuDTO.builder()
                .id(l.getId()).nombre(l.getNombre()).pais(l.getPais())
                .division(l.getDivision()).equipos(equipos)
                .build();
    }

    // Usuario -> UsuarioDTO (sin la contraseña).
    public static UsuarioDTO toUsuarioDTO(Usuario u) {
        return UsuarioDTO.builder().id(u.getId()).nombre(u.getNombre()).rol(u.getRol()).build();
    }

    // PedidoItem -> PedidoItemDTO.
    public static PedidoItemDTO toPedidoItemDTO(PedidoItem i) {
        Camiseta c = i.getCamiseta();
        return PedidoItemDTO.builder()
                .id(i.getId())
                .camisetaId(c != null ? c.getId() : null)
                .camisetaNombre(c != null ? c.getNombre() : null)
                .imagenUrl(c != null ? c.getImagenUrl() : null)
                .cantidad(i.getCantidad()).precioUnit(i.getPrecioUnit()).talla(i.getTalla())
                .nombreJugador(i.getNombreJugador()).dorsal(i.getDorsal())
                .build();
    }

    // Pedido -> PedidoDTO (con sus lineas y los datos del cliente).
    public static PedidoDTO toPedidoDTO(Pedido p) {
        Usuario u = p.getUsuario();
        List<PedidoItemDTO> items = (p.getItems() == null) ? List.of()
                : p.getItems().stream().map(DTOMapper::toPedidoItemDTO).collect(Collectors.toList());
        return PedidoDTO.builder()
                .id(p.getId())
                .usuarioId(u != null ? u.getId() : null)
                .clienteNombre(u != null ? u.getNombre() : null)
                .clienteEmail(u != null ? u.getEmail() : null)
                .fecha(p.getFecha()).total(p.getTotal()).estado(p.getEstado())
                .codigoPromo(p.getCodigoPromo()).descuento(p.getDescuento()).envio(p.getEnvio())
                .envioNombre(p.getEnvioNombre()).envioApellidos(p.getEnvioApellidos())
                .envioPais(p.getEnvioPais()).envioRegion(p.getEnvioRegion())
                .envioCiudad(p.getEnvioCiudad()).envioCp(p.getEnvioCp())
                .envioDireccion(p.getEnvioDireccion()).envioTelefono(p.getEnvioTelefono())
                .items(items)
                .build();
    }
}
