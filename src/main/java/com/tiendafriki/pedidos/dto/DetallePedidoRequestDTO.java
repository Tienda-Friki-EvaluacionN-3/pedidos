package com.tiendafriki.pedidos.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

// Este DTO sirve para controlar los datos que ingresa el usuario para creaer un detalle de pedido

@Data
public class DetallePedidoRequestDTO {

    @NotNull(message = "[ERROR] El productoId no puede estar vacio [X_X] ...")
    private Integer productoId;

    @Positive(message = "[ERROR] La cantidad debe ser un numero positivo [X_X] ...")
    @NotNull(message = "[ERROR] La cantidad no puede estar vacia [X_X] ...")
    private Integer cantidad;

    @Positive(message = "[ERROR] El precio debe ser un numero positivo [X_X] ...")
    @NotNull(message = "[ERROR] El precio no puede estar vacio [X_X] ...")
    private Integer precio;
}
