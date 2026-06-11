package com.footystck.repository;

import com.footystck.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Acceso a la tabla "pedidos".
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Pedidos de un usuario, del mas nuevo al mas antiguo.
    List<Pedido> findByUsuarioIdOrderByFechaDesc(Long usuarioId);
}
