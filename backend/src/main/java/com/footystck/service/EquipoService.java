package com.footystck.service;

import com.footystck.dto.CrearEquipoRequest;
import com.footystck.dto.DTOMapper;
import com.footystck.dto.EquipoDTO;
import com.footystck.model.Equipo;
import com.footystck.repository.EquipoRepository;
import com.footystck.repository.LigaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// Logica de equipos.
@Service
public class EquipoService {

    private final EquipoRepository equipoRepository;
    private final LigaRepository ligaRepository;

    public EquipoService(EquipoRepository equipoRepository, LigaRepository ligaRepository) {
        this.equipoRepository = equipoRepository;
        this.ligaRepository = ligaRepository;
    }

    // Lista equipos filtrando por liga, por "otros" o por nombre.
    public List<EquipoDTO> listar(Long ligaId, Boolean otros, String buscar) {
        return equipoRepository.findAll().stream()
                .filter(e -> ligaId == null || (e.getLiga() != null && ligaId.equals(e.getLiga().getId())))
                .filter(e -> otros == null || !otros || Boolean.TRUE.equals(e.getEsOtros()))
                .filter(e -> buscar == null || (e.getNombre() != null && e.getNombre().toLowerCase().contains(buscar.toLowerCase())))
                .map(DTOMapper::toEquipoDTO)
                .collect(Collectors.toList());
    }

    // Crea un equipo y devuelve su id.
    public Long crear(CrearEquipoRequest req) {
        Equipo e = new Equipo();
        aplicarDatos(e, req);
        return equipoRepository.save(e).getId();
    }

    // Edita un equipo existente.
    public void actualizar(Long id, CrearEquipoRequest req) {
        Equipo e = equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        aplicarDatos(e, req);
        equipoRepository.save(e);
    }

    // Borra un equipo por id.
    public void eliminar(Long id) {
        equipoRepository.deleteById(id);
    }

    // Copia los datos del formulario a la entidad.
    private void aplicarDatos(Equipo e, CrearEquipoRequest req) {
        e.setNombre(req.getNombre());
        e.setEscudoUrl(req.getEscudoUrl());
        e.setEsOtros(req.getEsOtros() != null ? req.getEsOtros() : false);
        e.setLiga(req.getLigaId() != null ? ligaRepository.findById(req.getLigaId()).orElse(null) : null);
    }
}
