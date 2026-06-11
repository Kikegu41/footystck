package com.footystck.repository;

import com.footystck.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Acceso a la tabla "usuarios". JpaRepository ya da save/findById/findAll/delete...
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Busca un usuario por su email.
    Optional<Usuario> findByEmail(String email);

    // ¿Existe ya un usuario con ese email?
    boolean existsByEmail(String email);
}
