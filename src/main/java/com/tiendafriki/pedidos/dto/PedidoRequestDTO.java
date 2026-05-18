package com.tiendafriki.pedidos.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class PedidoRequestDTO {

    @NotNull(message = "[+] El id del carrito no puede estar vacio...")
    private Integer carritoId;

    @Size(max = 100, message = "[+] El correo no puede tener más de 100 caracteres...")
    @Email(message = "[+] El correo debe ser valido...")
    @NotBlank(message = "[+] El correo no puede estar vacio...")
    private String email;

    @Pattern(regexp = "[0-9]{8}", message = "[+] El telefono debe tener exactamente 8 numeros...")
    @NotBlank(message = "[+] El telefono no debe estar vacio...")
    private String telefono;

    @Size(max = 150, message = "[+] La direccion no puede tener más de 150 caracteres...")
    @NotBlank(message = "[+] La direccion no puede estar vacia...")
    private String direccion;

    @Valid
    @NotEmpty(message = "[+] El pedido debe tener al menos un detalle...")
    private List<DetallePedidoRequestDTO> detalles;
}
