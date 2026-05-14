package com.tiendafriki.pedidos.repository;

import com.tiendafriki.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{


    Optional<Pedido> findBytelefono(String telefono);

    Optional<Pedido> findByDireccion(String direccion);

}
