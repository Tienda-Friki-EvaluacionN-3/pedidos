package com.tiendafriki.pedidos.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Pedido {

       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Integer id;

       @NotNull(message = "[ERROR] El id del carrito no puede estar vacio [X_X]...")
       private Integer carritoId;

       @Size(max = 100, message = "[ERROR] El correo no puede tener más de 100 caracteres [X_X] ...")
       @Email(message = "[ERROR] El correo debe ser valido [X_X] ...")
       @NotBlank(message = "[ERROR] El correo no puede estar vacio [X_X] ...")
       private String email;

       @Pattern(regexp = "[0-9]{8}", message = "[ERROR] El telefono debe tener exactamente 8 numeros [X_X]...")
       @Size(max = 8, message = "[ERROR] El telefono debe tener máximo 8 digitos [X_X] ...")
       @NotBlank(message = "[ERROR] El telefono no debe estar vacio [X_X] ... ")
       private String telefono;

       @Size(max = 150, message = "[ERROR] La direccion no puede tener más de 150 caracteres [X_X] ...")
       @NotBlank(message = "[ERROR] La direccion no puede estar vacio [X_X] ...")
       private String direccion;

       @Positive(message = "[ERROR] El total debe ser un numero positivo [X_X] ...")
       @NotNull(message = "[ERROR] El total no puede estar vacio [X_X] ...")
       private Integer total;

       @NotNull(message = "[ERROR] La fecha no puede estar vacia [X_X] ...")
       
       private LocalDate fechaRegistro;

       @Pattern(regexp = "(?i)Creado|Pagado|Reembolsado", message = "[ERROR] El estado debe ser Creado, Pagado, Reembolsado o Cancelado...")
       @NotBlank(message = "[ERROR] El estado no puede estar vacio [X_X] ...")
       private String estado;

       @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
       private List<DetallePedido> detalles;

}
