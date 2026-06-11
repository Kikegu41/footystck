package com.footystck.repository;

import com.footystck.model.Camiseta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Acceso a la tabla "camisetas" (CRUD basico heredado de JpaRepository).
@Repository
public interface CamisetaRepository extends JpaRepository<Camiseta, Long> {
}
