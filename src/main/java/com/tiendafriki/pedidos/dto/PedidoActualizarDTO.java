package com.tiendafriki.pedidos.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PedidoActualizarDTO {

    @Size(max = 100,
            message = "[ERROR] El correo no puede tener más de 100 caracteres [X_X] ...")
    @Email(message = "[ERROR] El correo debe ser valido [X_X] ...")
    @NotBlank(message = "[ERROR] El correo no puede estar vacio [X_X] ...")
    private String email;

    @Pattern(
            regexp = "[0-9]{8}",
            message = "[ERROR] El telefono debe tener exactamente 8 numeros [X_X] ..."
    )
    @NotBlank(message = "[ERROR] El telefono no debe estar vacio [X_X] ...")
    private String telefono;

    @Size(
            max = 150,
            message = "[ERROR] La direccion no puede tener más de 150 caracteres [X_X] ..."
    )
    @NotBlank(message = "[ERROR] La direccion no puede estar vacia [X_X] ...")
    private String direccion;

}
