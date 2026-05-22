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

import jakarta.validation.Valid;

import com.tiendafriki.pedidos.dto.PedidoActualizarDTO;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    // CONSTRUCTOR DE CONTROLLER E INYECCION DEL SERVICE

    private PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    // === GET: LISTAR === //

    @GetMapping("/listar")
    public List<Pedido> listar() {
        return service.listar();
    }

    // === GET: BUSCAR POR ID === //

    @GetMapping("/buscarId/{id}")
    public Pedido buscarPorId(@PathVariable Integer id) {

        return service.buscarPorId(id);
    }

    // === GET: BUSCAR POR TELEFONO === //

    @GetMapping("/buscarPorTelefono/{telefono}")
    public List<Pedido> buscarPorTelefono(@PathVariable String telefono) {
        return service.buscarPorTelefono(telefono);
    }
    
    // === GET: BUSCAR POR DIRECCION === //

    @GetMapping("/buscarPorDireccion/{direccion}")
    public List<Pedido>  buscarPorDireccion(@PathVariable String direccion) {
        return service.buscarPorDireccion(direccion);
    }

    // === POST: AGREGAR  === //

    @PostMapping("/agregar")
    public ResponseEntity<String> crearPedido(@Valid @RequestBody PedidoRequestDTO pedidoDTO) {
        String mensaje = service.guardar(pedidoDTO);
        return ResponseEntity.status(201).body(mensaje);
    }

    // NUEVO:

    // === PUT: ACTUALIZAR POR ID === //

    // Ahora se ingresa el id por la url, y se usa un PedidoActualizarDTO
    // para controlar el ingreso de los datos por el usuario
    // Solo permitirá cambiar el email, direccion y el telefono.

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id,
            @Valid @RequestBody PedidoActualizarDTO dto) {
        
        // Ingresamos el id y el DTO al servicio de actualizar
        // y guardamos el mensaje

        String mensaje = service.actualizar(id, dto);

        // Retornamos la respuesta HTTP con el mensaje

        return ResponseEntity.ok(mensaje);
    }

    // === DELETE: ELIMINAR POR ID === //

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        String mensaje = service.eliminar(id);
        return ResponseEntity.status(200).body(mensaje);

    }

    // NUEVO:

    // PUT: Marcar pedido como pagado:

    // Este endpoint se agregó para permitir que el pedido se marque automaticamnete
    // como pagado
    // cuando el el microservicio pago se efectua correctamente

    // Pago mandará una petición HTTP a pedido para que actualice el estado a pagado automaticamente

    @PutMapping("/{id}/pagado")
    public ResponseEntity<String> marcarComoPagado(@PathVariable Integer id) {

        String mensaje = service.marcarComoPagado(id);

        return ResponseEntity.ok(mensaje);
    }

    // === PUT: Marcar pedido como rembolsado: === //

    // Este endpoint se agregó para permitir que el pedido se marque automaticamnete como rembolsado
    // cuando el el microservicio devoluciones se efetua correctamente

    // Pago mandará una petición HTTP a pedido para que actualice el estado a rembolsado automaticamente

    @PutMapping("/{id}/reembolsado")
    public ResponseEntity<String> marcarComoReembolsado(@PathVariable Integer id){

        String mensaje = service.marcarComoReembolsado(id);

        return ResponseEntity.ok(mensaje);
    }

}
