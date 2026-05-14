package com.tiendafriki.pedidos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiendafriki.pedidos.model.DetallePedido;
import com.tiendafriki.pedidos.model.Pedido;
import com.tiendafriki.pedidos.repository.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    public List<Pedido> listar() {
        return repository.findAll();
    }

    public Optional<Pedido> buscarPorId(Integer id) {
        return repository.findById(id);
    }

    public Optional<Pedido> buscarPorTelefono(String telefono) {
        return repository.findBytelefono(telefono);
    }

    public Optional<Pedido> buscarPorDireccion(String direccion) {
        return repository.findByDireccion(direccion);
    }

    public String guardar(Pedido pedido) {
        List<Pedido> lista = repository.findAll();
        for (Pedido p : lista) {
            if (p.getEmail().equalsIgnoreCase(pedido.getEmail())) {
                return "[+] El pedido ya existe!";
            }
        }
        if (pedido.getDetalles() != null) {
            for (DetallePedido detalle : pedido.getDetalles()) {
                detalle.setPedido(pedido);
            }
        }
        repository.save(pedido);
        return "[+] El pedido fue agregado correctamente";
    }

    public String actualizar(Pedido pedido) {
        List<Pedido> lista = repository.findAll();
        for (Pedido p : lista) {
            if (p.getId().equals(pedido.getId())) {
                repository.save(pedido);
                return "[+] El pedido fue actualizado correctamente";
            }
        }
        return "[+] El pedido no fue encontrado";

    }

    public String eliminar(Integer id) {
        List<Pedido> lista = repository.findAll();
        for (Pedido p : lista) {
            if (p.getId().equals(id)) {
                repository.deleteById(id);
                return "[+] El pedido fue eliminado correctamente";
            }

        }
        return "[+] El pedido no fue encontrado";
    }

}
