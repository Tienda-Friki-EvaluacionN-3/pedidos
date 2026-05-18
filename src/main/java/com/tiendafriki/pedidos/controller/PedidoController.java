package com.tiendafriki.pedidos.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public List<Pedido> listar() {
        return service.listar();
    }

    @GetMapping("/buscarId/{id}")
    public Optional<Pedido> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/buscarPorTelefono/{telefono}")
    public Optional<Pedido> buscarPorTelefono(@PathVariable String telefono) {
        return service.buscarPorTelefono(telefono);
    }

    @GetMapping("/buscarPorDireccion/{direccion}")
    public Optional<Pedido> buscarPorDireccion(@PathVariable String direccion) {
        return service.buscarPorDireccion(direccion);
    }

    @PostMapping("/agregar")
    public ResponseEntity<String> crearPedido(@Valid @RequestBody PedidoRequestDTO pedidoDTO) {
        String mensaje = service.guardar(pedidoDTO);
        return ResponseEntity.status(201).body(mensaje);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizar(@Valid @RequestBody Pedido pedido) {
        String mensaje = service.actualizar(pedido);
        return ResponseEntity.status(200).body(mensaje);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        String mensaje = service.eliminar(id);
        return ResponseEntity.status(200).body(mensaje);

    }

    // NUEVO (CONY):

    // PUT: Marcar pedido como pagado:

    // Este endpoint se agregó para permitir que el pedido se marque automaticamnete
    // como pagado
    // cuando el el microservicio pago se efetua correctamente

    // Pago mandará una petición HTTP a pedido para que actualice el estado a pagado

    @PutMapping("/{id}/pagado")
    public ResponseEntity<String> marcarComoPagado(@PathVariable Integer id) {

        String mensaje = service.marcarComoPagado(id);

        return ResponseEntity.ok(mensaje);
    }

}
