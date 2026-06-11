package com.footystck.controller;

import com.footystck.service.SyncService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

// Endpoint para importar ligas y equipos desde API-Football (solo admin).
@RestController
@RequestMapping("/api/sync")
public class SyncController {

    private final SyncService syncService;

    public SyncController(SyncService syncService) {
        this.syncService = syncService;
    }

    // Lanza la sincronizacion de ligas y equipos.
    @PostMapping("/ligas")
    public Map<String, Object> sincronizar() {
        return syncService.sincronizar();
    }
}
