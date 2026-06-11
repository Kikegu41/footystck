package com.footystck.controller;

import com.footystck.dto.CrearEquipoRequest;
import com.footystck.dto.EquipoDTO;
import com.footystck.service.EquipoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// Endpoints de equipos.
@RestController
@RequestMapping("/api/equipos")
public class EquipoController {

    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    // Lista equipos con filtros opcionales (liga, "otros", busqueda).
    @GetMapping
    public List<EquipoDTO> listar(
            @RequestParam(name = "liga_id", required = false) Long ligaId,
            @RequestParam(required = false) Boolean otros,
            @RequestParam(required = false) String buscar) {
        return equipoService.listar(ligaId, otros, buscar);
    }

    // Crea un equipo (solo admin).
    @PostMapping
    public Map<String, Long> crear(@Valid @RequestBody CrearEquipoRequest request) {
        return Map.of("id", equipoService.crear(request));
    }

    // Edita un equipo (solo admin).
    @PutMapping("/{id}")
    public Map<String, String> actualizar(@Valid @RequestBody CrearEquipoRequest request, @PathVariable Long id) {
        equipoService.actualizar(id, request);
        return Map.of("mensaje", "Equipo actualizado");
    }

    // Borra un equipo (solo admin).
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Long id) {
        equipoService.eliminar(id);
        return Map.of("mensaje", "Equipo eliminado");
    }
}
