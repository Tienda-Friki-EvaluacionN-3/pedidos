package com.tiendafriki.pedidos.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

// Este DTO sirve para controlar los datos que ingresa el usuario para crear un pedido

@Data
public class PedidoRequestDTO {

    @NotNull(message = "[ERROR] El id del carrito no puede estar vacio [X_X] ...")
    private Integer carritoId;

    @Size(max = 100, message = "[ERROR] El correo no puede tener más de 100 caracteres...")
    @Email(message = "[ERROR] El correo debe ser valido [X_X] ...")
    @NotBlank(message = "[ERROR] El correo no puede estar vacio [X_X] ...")
    private String email;

    @Pattern(regexp = "[0-9]{8}", message = "[ERROR] El telefono debe tener exactamente 8 numeros [X_X]...")
    @NotBlank(message = "[ERROR] El telefono no debe estar vacio [X_X] ...")
    private String telefono;

    @Size(max = 150, message = "[ERROR] La direccion no puede tener más de 150 caracteres [X_X] ...")
    @NotBlank(message = "[ERROR] La direccion no puede estar vacia [X_X]...")
    private String direccion;

    @Valid
    @NotEmpty(message = "[ERROR] El pedido debe tener al menos un detalle [X_X] ...")
    private List<DetallePedidoRequestDTO> detalles;
}
