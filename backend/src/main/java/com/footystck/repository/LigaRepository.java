package com.footystck.repository;

import com.footystck.model.Liga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Acceso a la tabla "ligas".
@Repository
public interface LigaRepository extends JpaRepository<Liga, Long> {

    // Busca una liga por su id de API-Football.
    Optional<Liga> findByCodigoApi(Integer codigoApi);
}
