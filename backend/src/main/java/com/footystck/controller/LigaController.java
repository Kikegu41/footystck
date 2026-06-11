package com.footystck.controller;

import com.footystck.dto.CrearLigaRequest;
import com.footystck.dto.LigaMenuDTO;
import com.footystck.model.Liga;
import com.footystck.service.LigaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// Endpoints de ligas.
@RestController
@RequestMapping("/api/ligas")
public class LigaController {

    private final LigaService ligaService;

    public LigaController(LigaService ligaService) {
        this.ligaService = ligaService;
    }

    // Lista todas las ligas.
    @GetMapping
    public List<Liga> listar() {
        return ligaService.listar();
    }

    // Devuelve las ligas con sus equipos (para el menu de categorias).
    @GetMapping("/menu")
    public List<LigaMenuDTO> menu() {
        return ligaService.menu();
    }

    // Crea una liga nueva (solo admin).
    @PostMapping
    public Map<String, Long> crear(@Valid @RequestBody CrearLigaRequest request) {
        return Map.of("id", ligaService.crear(request));
    }

    // Edita una liga existente (solo admin).
    @PutMapping("/{id}")
    public Map<String, String> actualizar(@Valid @RequestBody CrearLigaRequest request, @PathVariable Long id) {
        ligaService.actualizar(id, request);
        return Map.of("mensaje", "Liga actualizada");
    }
}
