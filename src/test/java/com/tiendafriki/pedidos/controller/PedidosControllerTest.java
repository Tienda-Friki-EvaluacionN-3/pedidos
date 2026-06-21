package com.tiendafriki.pedidos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.tiendafriki.pedidos.dto.DetallePedidoRequestDTO;
import com.tiendafriki.pedidos.dto.PedidoActualizarDTO;
import com.tiendafriki.pedidos.dto.PedidoRequestDTO;
import com.tiendafriki.pedidos.model.Pedido;
import com.tiendafriki.pedidos.service.PedidoService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PedidoController.class)
public class PedidosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PedidoService service;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Pedido crearPedido() {
        return new Pedido(
                1,
                1,
                "FelipitoPeruano@correo.cl",
                "12345678",
                "Av. Av. Siempre pé 123",
                43000,
                LocalDate.now(),
                "Creado",
                new ArrayList<>());
    }

    private PedidoRequestDTO crearPedidoRequestDTO() {

        DetallePedidoRequestDTO detalle = new DetallePedidoRequestDTO();
        detalle.setProductoId(1);
        detalle.setCantidad(2);
        detalle.setPrecio(10000);

        PedidoRequestDTO dto = new PedidoRequestDTO();
        dto.setCarritoId(1);
        dto.setEmail("FelipitoPeruano@correo.cl");
        dto.setTelefono("12345678");
        dto.setDireccion("Av. Siempre pé 123");
        dto.setDetalles(List.of(detalle));

        return dto;
    }

    private PedidoActualizarDTO crearPedidoActualizarDTO() {

        PedidoActualizarDTO dto = new PedidoActualizarDTO();
        dto.setEmail("FelipitoActualizaedo@correo.cl");
        dto.setTelefono("77777777");
        dto.setDireccion("Av. Siempre pé 444");

        return dto;
    }

    
    @Test
    void listarPedidos() throws Exception {

        when(service.listar())
                .thenReturn(List.of(crearPedido()));

        mockMvc.perform(get("/pedidos/listar"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPedidoPorId() throws Exception {

        when(service.buscarPorId(1))
                .thenReturn(crearPedido());

        mockMvc.perform(get("/pedidos/buscarId/1"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPedidoPorTelefono() throws Exception {

        when(service.buscarPorTelefono("12345678"))
                .thenReturn(List.of(crearPedido()));

        mockMvc.perform(get("/pedidos/buscarPorTelefono/12345678"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPedidoPorDireccion() throws Exception {

        when(service.buscarPorDireccion("Av. Siempre pé 123"))
                .thenReturn(List.of(crearPedido()));

        mockMvc.perform(get("/pedidos/buscarPorDireccion/Av. Siempre pé 123"))
                .andExpect(status().isOk());
    }

    @Test
    void agregarPedido() throws Exception {

        when(service.guardar(any()))
                .thenReturn("[+] El pedido fue agregado correctamente");

        mockMvc.perform(post("/pedidos/agregar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(crearPedidoRequestDTO())))
                .andExpect(status().isCreated());
    }

    @Test
    void actualizarPedido() throws Exception {

        when(service.actualizar(any(), any()))
                .thenReturn("[+] El pedido fue actualizado correctamente");

        mockMvc.perform(put("/pedidos/actualizar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(crearPedidoActualizarDTO())))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarPedido() throws Exception {

        when(service.eliminar(1))
                .thenReturn("[+] El pedido fue eliminado correctamente");

        mockMvc.perform(delete("/pedidos/eliminar/1"))
                .andExpect(status().isOk());
    }

    @Test
    void marcarPedidoComoPagado() throws Exception {

        when(service.marcarComoPagado(1))
                .thenReturn("[+] Pedido actualizado a PAGADO");

        mockMvc.perform(put("/pedidos/1/pagado"))
                .andExpect(status().isOk());
    }

    @Test
    void marcarPedidoComoReembolsado() throws Exception {

        when(service.marcarComoReembolsado(1))
                .thenReturn("[+] Pedido actualizado a REEMBOLSADO");

        mockMvc.perform(put("/pedidos/1/reembolsado"))
                .andExpect(status().isOk());
    }
}
