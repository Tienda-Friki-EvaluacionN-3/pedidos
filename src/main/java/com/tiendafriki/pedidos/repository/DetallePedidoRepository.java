package com.tiendafriki.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tiendafriki.pedidos.model.DetallePedido;
import org.springframework.stereotype.Repository;


@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {

    // Este repositorio podría contener funciones propias para consultar detalles de pedidos
    
}
