package com.footystck.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.footystck.model.Equipo;
import com.footystck.model.Liga;
import com.footystck.repository.EquipoRepository;
import com.footystck.repository.LigaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

// Importa ligas y equipos desde API-Football a la base de datos.
@Service
public class SyncService {

    private final LigaRepository ligaRepository;
    private final EquipoRepository equipoRepository;

    @Value("${apifootball.key}")
    private String apiKey;

    @Value("${apifootball.season}")
    private String season;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    // Ligas a importar: { codigoApi, nombre, pais, division }
    private static final Object[][] LIGAS = {
            {140, "LaLiga", "España", 1}, {141, "LaLiga 2", "España", 2},
            {39, "Premier League", "Inglaterra", 1}, {40, "Championship", "Inglaterra", 2},
            {61, "Ligue 1", "Francia", 1}, {78, "Bundesliga", "Alemania", 1},
            {79, "2. Bundesliga", "Alemania", 2}, {135, "Serie A", "Italia", 1},
            {128, "Liga Profesional", "Argentina", 1}, {71, "Serie A", "Brasil", 1},
            {88, "Eredivisie", "Holanda", 1}, {144, "Jupiler Pro League", "Bélgica", 1},
            {179, "Premiership", "Escocia", 1}, {98, "J1 League", "Japón", 1},
            {262, "Liga MX", "México", 1}, {253, "Major League Soccer", "Estados Unidos", 1},
            {307, "Saudi Pro League", "Arabia Saudí", 1}
    };

    public SyncService(LigaRepository ligaRepository, EquipoRepository equipoRepository) {
        this.ligaRepository = ligaRepository;
        this.equipoRepository = equipoRepository;
    }

    // Recorre las ligas, las guarda y descarga sus equipos. Devuelve un resumen.
    public Map<String, Object> sincronizar() {
        if (apiKey == null || apiKey.isBlank()) {
            throw new RuntimeException("Falta apifootball.key en application.properties");
        }

        List<Map<String, Object>> resumen = new ArrayList<>();
        for (Object[] datos : LIGAS) {
            Liga liga = guardarLiga((Integer) datos[0], (String) datos[1], (String) datos[2], (Integer) datos[3]);
            try {
                int n = importarEquipos((Integer) datos[0], liga);
                resumen.add(Map.of("liga", datos[1], "equipos_importados", n));
            } catch (Exception e) {
                resumen.add(Map.of("liga", datos[1], "error", e.getMessage()));
            }
        }
        return Map.of("mensaje", "Sincronizacion terminada", "temporada", season, "resumen", resumen);
    }

    // Inserta o actualiza una liga por su codigo de API.
    private Liga guardarLiga(Integer codigoApi, String nombre, String pais, Integer division) {
        Liga liga = ligaRepository.findByCodigoApi(codigoApi).orElse(new Liga());
        liga.setNombre(nombre);
        liga.setPais(pais);
        liga.setDivision(division);
        liga.setCodigoApi(codigoApi);
        return ligaRepository.save(liga);
    }

    // Llama a la API y guarda los equipos de una liga. Devuelve cuantos importo.
    private int importarEquipos(Integer ligaApiId, Liga liga) throws Exception {
        String url = "https://v3.football.api-sports.io/teams?league=" + ligaApiId + "&season=" + season;

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-apisports-key", apiKey);
        ResponseEntity<String> respuesta = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);

        JsonNode lista = mapper.readTree(respuesta.getBody()).get("response");
        int contador = 0;
        if (lista != null) {
            for (JsonNode item : lista) {
                JsonNode team = item.get("team");
                guardarEquipo(team.get("id").asInt(), team.get("name").asText(), team.get("logo").asText(), liga);
                contador++;
            }
        }
        return contador;
    }

    // Inserta o actualiza un equipo por su codigo de API.
    private void guardarEquipo(Integer codigoApi, String nombre, String escudo, Liga liga) {
        Equipo equipo = equipoRepository.findByCodigoApi(codigoApi).orElse(new Equipo());
        equipo.setNombre(nombre);
        equipo.setEscudoUrl(escudo);
        equipo.setLiga(liga);
        equipo.setCodigoApi(codigoApi);
        equipoRepository.save(equipo);
    }
}
