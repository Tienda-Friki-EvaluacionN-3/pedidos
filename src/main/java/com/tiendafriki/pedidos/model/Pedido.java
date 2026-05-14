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

       @Size(max = 100, message = "[+] El correo no puede tener más de 100 caracteres...")
       @Email(message = "[+] El correo debe ser valido...")
       @NotBlank(message = "[+] El correo no puede estar vacio...")
       @Column(unique = true)
       private String email;

       @Pattern(regexp = "[0-9]{8}", message = "[+] El telefono debe tener exactamente 8 numeros...")
       @Size(max = 8, message = "[+] El telefono debe tener máximo 8 digitos...")
       @NotBlank(message = "[+] El telefono no debe estar vacio... ")
       private String telefono;

       @Size(max = 150, message = "[+] La direccion no puede tener más de 150 caracteres...")
       @NotBlank(message = "[+] La direccion no puede estar vacio...")
       private String direccion;

       @Positive(message = "[+] El total debe ser un numero positivo...")
       @NotNull(message = "[+] El total no puede estar vacio...")
       private Integer total;

       @NotBlank(message = "[+] La fecha no puede estar vacia...")
       private String fechaRegistro;
       // private LocalDate fechaRegistro;

       // @Pattern(regexp = "(?i)Pendiente|Enviado|Cancelado|Reservado", message = "[+]
       // El estado debe ser Pendiente, Enviado, Cancelado o Reservado...")
       @NotBlank(message = "[+] El estado no puede estar vacio...")
       private String estado;

       @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
       private List<DetallePedido> detalles;

}
