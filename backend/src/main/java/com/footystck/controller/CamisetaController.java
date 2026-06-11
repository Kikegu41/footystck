package com.footystck.controller;

import com.footystck.dto.CamisetaDTO;
import com.footystck.dto.CrearCamisetaRequest;
import com.footystck.service.CamisetaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// Endpoints del catalogo de camisetas.
@RestController
@RequestMapping("/api/camisetas")
public class CamisetaController {

    private final CamisetaService camisetaService;

    public CamisetaController(CamisetaService camisetaService) {
        this.camisetaService = camisetaService;
    }

    // Lista camisetas con filtros opcionales (coleccion, equipo, liga, busqueda, orden, limite).
    @GetMapping
    public List<CamisetaDTO> listar(
            @RequestParam(required = false) String coleccion,
            @RequestParam(name = "equipo_id", required = false) Long equipoId,
            @RequestParam(name = "liga_id", required = false) Long ligaId,
            @RequestParam(required = false) String buscar,
            @RequestParam(required = false) String orden,
            @RequestParam(required = false) Integer limite) {
        return camisetaService.listar(coleccion, equipoId, ligaId, buscar, orden, limite);
    }

    // Devuelve una camiseta por su id.
    @GetMapping("/{id}")
    public CamisetaDTO obtener(@PathVariable Long id) {
        return camisetaService.obtener(id);
    }

    // Crea una camiseta (solo admin).
    @PostMapping
    public Map<String, Long> crear(@Valid @RequestBody CrearCamisetaRequest request) {
        return Map.of("id", camisetaService.crear(request));
    }

    // Edita una camiseta (solo admin).
    @PutMapping("/{id}")
    public Map<String, String> actualizar(@Valid @RequestBody CrearCamisetaRequest request, @PathVariable Long id) {
        camisetaService.actualizar(id, request);
        return Map.of("mensaje", "Camiseta actualizada");
    }

    // Borra una camiseta (solo admin).
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Long id) {
        camisetaService.eliminar(id);
        return Map.of("mensaje", "Camiseta eliminada");
    }
}
