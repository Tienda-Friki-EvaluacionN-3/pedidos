package com.tiendafriki.pedidos.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tiendafriki.pedidos.dto.PedidoRequestDTO;

import com.tiendafriki.pedidos.model.Pedido;
import com.tiendafriki.pedidos.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import com.tiendafriki.pedidos.dto.PedidoActualizarDTO;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @Operation(summary = "Listar pedidos", description = "Obtiene una lista con todos los pedidos registrados en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    
    @GetMapping("/listar")
    public List<Pedido> listar() {
        return service.listar();
    }

    @Operation(summary = "Buscar pedido por ID", description = "Busca un pedido según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/buscarId/{id}")
    public Pedido buscarPorId(@PathVariable Integer id) {

        return service.buscarPorId(id);
    }

    @Operation(summary = "Buscar pedidos por teléfono", description = "Busca pedidos vinculados a un número de teléfono")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron pedidos con ese teléfono"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/buscarPorTelefono/{telefono}")
    public List<Pedido> buscarPorTelefono(@PathVariable String telefono) {
        return service.buscarPorTelefono(telefono);
    }

    @Operation(summary = "Buscar pedidos por dirección", description = "Busca pedidos vinculados a una dirección")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron pedidos con esa dirección"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/buscarPorDireccion/{direccion}")
    public List<Pedido> buscarPorDireccion(@PathVariable String direccion) {
        return service.buscarPorDireccion(direccion);
    }

    @Operation(summary = "agregar pedido", description = "Agrega un nuevo pedido a partir de los datos ingresados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido agregado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o error de validación"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PostMapping("/agregar")
    public ResponseEntity<String> crearPedido(@Valid @RequestBody PedidoRequestDTO pedidoDTO) {
        String mensaje = service.guardar(pedidoDTO);
        return ResponseEntity.status(201).body(mensaje);
    }

    @Operation(summary = "Actualizar pedido", description = "Actualiza email, teléfono y dirección de un pedido existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id,
            @Valid @RequestBody PedidoActualizarDTO dto) {

        String mensaje = service.actualizar(id, dto);

        return ResponseEntity.ok(mensaje);
    }

    @Operation(summary = "Eliminar pedido", description = "Elimina un pedido vinculado a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        String mensaje = service.eliminar(id);
        return ResponseEntity.status(200).body(mensaje);

    }

    @Operation(summary = "Marcar pedido como pagado", description = "Actualiza el estado del pedido a Pagado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido marcado como pagado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PutMapping("/{id}/pagado")
    public ResponseEntity<String> marcarComoPagado(@PathVariable Integer id) {

        String mensaje = service.marcarComoPagado(id);

        return ResponseEntity.ok(mensaje);
    }

    @Operation(summary = "Marcar pedido como reembolsado", description = "Actualiza el estado del pedido a Reembolsado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido marcado como reembolsado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PutMapping("/{id}/reembolsado")
    public ResponseEntity<String> marcarComoReembolsado(@PathVariable Integer id) {

        String mensaje = service.marcarComoReembolsado(id);

        return ResponseEntity.ok(mensaje);
    }

}
