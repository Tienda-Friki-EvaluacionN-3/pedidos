package com.tiendafriki.pedidos.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class DetallePedidoRequestDTO {

    @NotNull(message = "[+] El productoId no puede estar vacio...")
    private Integer productoId;

    @Positive(message = "[+] La cantidad debe ser un numero positivo...")
    @NotNull(message = "[+] La cantidad no puede estar vacia...")
    private Integer cantidad;

    @Positive(message = "[+] El precio debe ser un numero positivo...")
    @NotNull(message = "[+] El precio no puede estar vacio...")
    private Integer precio;
}
