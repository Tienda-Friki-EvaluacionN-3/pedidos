package com.tiendafriki.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tiendafriki.pedidos.model.DetallePedido;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {

    
}
