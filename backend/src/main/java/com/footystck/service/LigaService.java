package com.footystck.service;

import com.footystck.dto.CrearLigaRequest;
import com.footystck.dto.DTOMapper;
import com.footystck.dto.LigaMenuDTO;
import com.footystck.model.Liga;
import com.footystck.repository.LigaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// Logica de ligas.
@Service
public class LigaService {

    private final LigaRepository ligaRepository;

    public LigaService(LigaRepository ligaRepository) {
        this.ligaRepository = ligaRepository;
    }

    // Devuelve todas las ligas.
    public List<Liga> listar() {
        return ligaRepository.findAll();
    }

    // Devuelve cada liga con sus equipos dentro (menu de categorias).
    public List<LigaMenuDTO> menu() {
        return ligaRepository.findAll().stream().map(DTOMapper::toLigaMenuDTO).collect(Collectors.toList());
    }

    // Crea una liga y devuelve su id.
    public Long crear(CrearLigaRequest req) {
        Liga liga = new Liga();
        aplicarDatos(liga, req);
        return ligaRepository.save(liga).getId();
    }

    // Edita una liga existente.
    public void actualizar(Long id, CrearLigaRequest req) {
        Liga liga = ligaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Liga no encontrada"));
        aplicarDatos(liga, req);
        ligaRepository.save(liga);
    }

    // Copia los datos del formulario a la entidad.
    private void aplicarDatos(Liga liga, CrearLigaRequest req) {
        liga.setNombre(req.getNombre());
        liga.setPais(req.getPais());
        liga.setDivision(req.getDivision() != null ? req.getDivision() : 1);
        liga.setCodigoApi(req.getCodigoApi());
    }
}
