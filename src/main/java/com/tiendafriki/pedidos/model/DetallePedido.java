package com.tiendafriki.pedidos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "detallePedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "[+] El productoId no puede estar vacio...")
    private Integer productoId;

    @Positive(message = "[+] La cantidad del producto debe ser un numero positivo...")
    @NotNull(message = "[+] La cantidad del prodcto no puede estar vacio...")
    private Integer cantidad;

    @Positive(message = "[+] El precio debe ser un numero positivo...")
    @NotNull(message = "[+] El precio no puede estar vacio...")
    private Integer precio;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @JsonIgnore
    private Pedido pedido;
}
