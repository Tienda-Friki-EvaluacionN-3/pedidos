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

    @NotNull(message = "[ERROR] El productoId no puede estar vacio [X_X] ...")
    private Integer productoId;

    @Positive(message = "[ERROR] La cantidad del producto debe ser un numero positivo [X_X] ...")
    @NotNull(message = "[ERROR] La cantidad del producto no puede estar vacio [X_X] ...")
    private Integer cantidad;

    @Positive(message = "[ERROR] El precio debe ser un numero positivo [X_X] ...")
    @NotNull(message = "[ERROR] El precio no puede estar vacio [X_X] ... ")
    private Integer precio;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @JsonIgnore
    private Pedido pedido;
}
