package com.tiendafriki.pedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PedidoResponseDTO {

    private Integer id;
    private Integer carritoId;
    private String email;
    private String telefono;
    private String direccion;
    private Integer total;
    private LocalDate fechaRegistro;
    private String estado;

}
