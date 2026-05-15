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



    // NUEVO (CONY):

    // PUT: Marcar pedido como pagado:

    // Esta función se agregó para permitir que el pedido se marque automaticamnete como pagado
    // cuando el el microservicio pago se efetua correctamente

    public String marcarComoPagado(Integer id){

        // Se buscar el pedido por id (segun el id indicado por pago) y se guarda en una variable:

        Optional<Pedido> pedidoOpt = repository.findById(id);

        // Se comprueba que el pedido exista:

        if(pedidoOpt.isEmpty()){

            return "[+] Pedido no encontrado";
        }

        //  Se crear un objeto pedido nuevo a partir de los datos del pedido que se desea actuaizar

        Pedido pedido = pedidoOpt.get();

        // Se actualiza el estado del pedido a pagado

        pedido.setEstado("PAGADO");

        // Se guarda el pedido actualizado

        repository.save(pedido);

        return "[+] Pedido actualizado a PAGADO";
    }


}
