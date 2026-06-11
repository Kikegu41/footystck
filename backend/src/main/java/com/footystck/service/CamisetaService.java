package com.footystck.service;

import com.footystck.dto.CamisetaDTO;
import com.footystck.dto.CrearCamisetaRequest;
import com.footystck.dto.DTOMapper;
import com.footystck.model.Camiseta;
import com.footystck.model.Equipo;
import com.footystck.repository.CamisetaRepository;
import com.footystck.repository.EquipoRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// Logica del catalogo de camisetas (filtra, ordena y crea/edita/borra).
@Service
public class CamisetaService {

    private final CamisetaRepository camisetaRepository;
    private final EquipoRepository equipoRepository;

    public CamisetaService(CamisetaRepository camisetaRepository, EquipoRepository equipoRepository) {
        this.camisetaRepository = camisetaRepository;
        this.equipoRepository = equipoRepository;
    }

    // Devuelve las camisetas aplicando los filtros, el orden y un limite opcional.
    public List<CamisetaDTO> listar(String coleccion, Long equipoId, Long ligaId, String buscar, String orden, Integer limite) {
        List<Camiseta> lista = camisetaRepository.findAll().stream()
                .filter(c -> coleccion == null || coleccion.equalsIgnoreCase(c.getColeccion()))
                .filter(c -> equipoId == null || (c.getEquipo() != null && equipoId.equals(c.getEquipo().getId())))
                .filter(c -> ligaId == null || (c.getEquipo() != null && c.getEquipo().getLiga() != null && ligaId.equals(c.getEquipo().getLiga().getId())))
                .filter(c -> buscar == null || contiene(c, buscar))
                .collect(Collectors.toList());

        if ("precio_asc".equals(orden)) lista.sort(Comparator.comparing(Camiseta::getPrecio));
        else if ("precio_desc".equals(orden)) lista.sort(Comparator.comparing(Camiseta::getPrecio).reversed());
        else if ("popularidad".equals(orden)) lista.sort(Comparator.comparingInt(this::ventas).reversed());
        else lista.sort((a, b) -> Boolean.compare(esDestacado(b), esDestacado(a)));

        if (limite != null && lista.size() > limite) lista = lista.subList(0, limite);

        return lista.stream().map(DTOMapper::toCamisetaDTO).collect(Collectors.toList());
    }

    // Devuelve una camiseta por id (o error si no existe).
    public CamisetaDTO obtener(Long id) {
        return DTOMapper.toCamisetaDTO(buscarOError(id));
    }

    // Crea una camiseta y devuelve su id.
    public Long crear(CrearCamisetaRequest req) {
        Camiseta c = new Camiseta();
        aplicarDatos(c, req);
        return camisetaRepository.save(c).getId();
    }

    // Edita una camiseta existente.
    public void actualizar(Long id, CrearCamisetaRequest req) {
        Camiseta c = buscarOError(id);
        aplicarDatos(c, req);
        camisetaRepository.save(c);
    }

    // Borra una camiseta por id.
    public void eliminar(Long id) {
        camisetaRepository.deleteById(id);
    }

    // Busca la camiseta o lanza un error.
    private Camiseta buscarOError(Long id) {
        return camisetaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Camiseta no encontrada"));
    }

    // Copia los datos del formulario a la entidad.
    private void aplicarDatos(Camiseta c, CrearCamisetaRequest req) {
        c.setNombre(req.getNombre());
        c.setTemporada(req.getTemporada());
        c.setTipo(req.getTipo() != null ? req.getTipo() : "local");
        c.setColeccion(req.getColeccion() != null ? req.getColeccion() : "actual");
        c.setPrecio(req.getPrecio());
        c.setStock(req.getStock() != null ? req.getStock() : 0);
        c.setImagenUrl(req.getImagenUrl());
        c.setDescripcion(req.getDescripcion());
        c.setDestacado(req.getDestacado() != null ? req.getDestacado() : false);
        c.setEquipo(req.getEquipoId() != null ? equipoRepository.findById(req.getEquipoId()).orElse(null) : null);
    }

    // ¿El texto buscado está en el nombre de la camiseta o del equipo?
    private boolean contiene(Camiseta c, String buscar) {
        String b = buscar.toLowerCase();
        boolean enNombre = c.getNombre() != null && c.getNombre().toLowerCase().contains(b);
        boolean enEquipo = c.getEquipo() != null && c.getEquipo().getNombre() != null && c.getEquipo().getNombre().toLowerCase().contains(b);
        return enNombre || enEquipo;
    }

    // ¿Está destacada?
    private boolean esDestacado(Camiseta c) {
        return c.getDestacado() != null && c.getDestacado();
    }

    // Unidades vendidas (0 si aún no tiene)
    private int ventas(Camiseta c) {
        return c.getVentas() != null ? c.getVentas() : 0;
    }
}
