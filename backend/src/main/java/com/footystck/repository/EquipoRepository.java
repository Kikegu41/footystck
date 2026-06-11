package com.footystck.repository;

import com.footystck.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Acceso a la tabla "equipos".
@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    // Busca un equipo por su id de API-Football.
    Optional<Equipo> findByCodigoApi(Integer codigoApi);
}
