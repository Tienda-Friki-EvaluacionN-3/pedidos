package com.tiendafriki.pedidos.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tiendafriki.pedidos.dto.PedidoRequestDTO;
import com.tiendafriki.pedidos.model.DetallePedido;
import com.tiendafriki.pedidos.model.Pedido;
import com.tiendafriki.pedidos.repository.PedidoRepository;
import com.tiendafriki.pedidos.dto.DetallePedidoRequestDTO;
import com.tiendafriki.pedidos.dto.PedidoActualizarDTO;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    public List<Pedido> listar() {
        return repository.findAll();
    }

    public Pedido buscarPorId(Integer id) {

        return repository.findById(id)

                .orElseThrow(() ->
                new NoSuchElementException
                ( "[ERROR] Pedido no encontrado [X_X] ..."));
    }

    public List<Pedido> buscarPorTelefono(String telefono) {

    List<Pedido> pedidos = repository.findBytelefono(telefono);

        if (pedidos.isEmpty()) {

            throw new NoSuchElementException(
                "[ERROR] No existen pedidos asociados a ese telefono [X_X] ...");
        }

        return pedidos;

    }
    
    public List<Pedido> buscarPorDireccion(String direccion) {

         List<Pedido> pedidos = repository.findByDireccion(direccion);

        if (pedidos.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] No existen pedidos asociados a esa direccion [X_X] ...");
        }

         return pedidos;
                
    }


    public String guardar(PedidoRequestDTO pedidoDTO) {

        validarCarrito(pedidoDTO.getCarritoId());

        validarProductosCarrito(
                pedidoDTO.getCarritoId(),
                pedidoDTO.getDetalles()
        );

        Pedido pedido = new Pedido();

        pedido.setCarritoId(pedidoDTO.getCarritoId());

        pedido.setEmail(pedidoDTO.getEmail());

        pedido.setTelefono(pedidoDTO.getTelefono());

        pedido.setDireccion(pedidoDTO.getDireccion());

        pedido.setFechaRegistro(LocalDate.now()); 

        pedido.setEstado("Creado"); 

        int total = 0; 

        List<DetallePedido> detalles = new ArrayList<>();

        for (DetallePedidoRequestDTO detalleDTO
                : pedidoDTO.getDetalles()) {

            DetallePedido detalle =
                    new DetallePedido();

            detalle.setProductoId(
                    detalleDTO.getProductoId()
            );

            detalle.setCantidad(
                    detalleDTO.getCantidad()
            );

            detalle.setPrecio(
                    detalleDTO.getPrecio()
            );

            detalle.setPedido(pedido);

            total += detalleDTO.getCantidad()
                    * detalleDTO.getPrecio();

            detalles.add(detalle);
        }


        total = (int) (total * 1.19);

        Integer totalCarrito =
                obtenerTotalCarrito(
                        pedidoDTO.getCarritoId()
                );

        if (!totalCarrito.equals(total)) {

            throw new IllegalArgumentException(
                    "[ERROR] El total del pedido no coincide con el carrito [X_X] ..."
            );
        }

        pedido.setTotal(total);

        pedido.setDetalles(detalles);

        repository.save(pedido);

        return "[+] El pedido fue agregado correctamente";
    }

    private void validarCarrito(Integer carritoId) {

        RestTemplate rt = new RestTemplate();

        String url =
                "http://localhost:8083/carrito/buscarxid/"
                        + carritoId;

        try {

            rt.getForObject(url, Object.class);

        } catch (Exception e) {

            throw new NoSuchElementException(
                    "[ERROR] El carrito no existe [X_X] ..."
            );
        }
    }

    private Integer obtenerTotalCarrito(Integer carritoId) {

        RestTemplate rt = new RestTemplate();

        String url =
                "http://localhost:8083/carrito/total/"
                        + carritoId;

        try {

            Double total =
                    rt.getForObject(url, Double.class);

            return total.intValue();

        } catch (Exception e) {

            throw new RuntimeException(
                    "[ERROR] No se pudo obtener el total del carrito [X_X] ..."
            );
        }
    }

    private void validarProductosCarrito(
            Integer carritoId,
            List<DetallePedidoRequestDTO> detallesDTO) {

        RestTemplate rt = new RestTemplate();

        String url =
                "http://localhost:8083/detalleCarrito/carrito/"
                        + carritoId;

        try {

            List<Map<String, Object>> detallesCarrito =

                    rt.getForObject(url, List.class);

            for (DetallePedidoRequestDTO dto : detallesDTO) {

                boolean existe =

                        detallesCarrito.stream()
                                .anyMatch(detalle ->
                                        detalle.get("productoId")
                                                .equals(dto.getProductoId())
                                );

                if (!existe) {

                    throw new IllegalArgumentException(
                            "[ERROR] El producto "
                                    + dto.getProductoId()
                                    + " no existe en el carrito [X_X] ..."
                    );
                }
            }

        }

        catch (IllegalArgumentException e) {

            throw e;
        }

        catch (Exception e) {

            throw new RuntimeException(
                    "[ERROR] Error al validar productos del carrito [X_X] ..."
            );
        }
    }

    public String actualizar(Integer id,PedidoActualizarDTO dto) {

        Optional<Pedido> pedidoOpt = repository.findById(id);

        if (pedidoOpt.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] Pedido no encontrado [X_X] ..."
            );
        }

        Pedido pedido = pedidoOpt.get();

        pedido.setEmail(dto.getEmail());

        pedido.setTelefono(dto.getTelefono());

        pedido.setDireccion(dto.getDireccion());

        repository.save(pedido);

        return "[+] El pedido fue actualizado correctamente";
    }

    public String eliminar(Integer id) {
        
        Optional<Pedido> pedidoOpt = repository.findById(id);

        if (pedidoOpt.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] Pedido no encontrado [X_X] ..."
            );
        }

        repository.deleteById(id);

        return "[+] El pedido fue eliminado correctamente";

    }

    public String marcarComoPagado(Integer id) {

        Optional<Pedido> pedidoOpt = repository.findById(id);

        if (pedidoOpt.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] Pedido no encontrado [X_X] ..."
            );
        }

        Pedido pedido = pedidoOpt.get();

        RestTemplate rt = new RestTemplate();

        for (DetallePedido detalle : pedido.getDetalles()) {

            String url =
                    "http://localhost:8081/catalogo/descontarstock/"
                            + detalle.getProductoId()
                            + "/"
                            + detalle.getCantidad();

            try {

                rt.put(url, null);

            } catch (Exception e) {

                throw new RuntimeException(
                        "[ERROR] No se pudo descontar stock del producto ID "
                                + detalle.getProductoId()
                                + " [X_X] ..."
                );
            }
        }

        pedido.setEstado("Pagado");

        repository.save(pedido);

        return "[+] Pedido actualizado a PAGADO";
    }

    public String marcarComoReembolsado(Integer id){

        Optional<Pedido> pedidoOpt = repository.findById(id);

        if(pedidoOpt.isEmpty()){

            throw new NoSuchElementException(
                    "[ERROR] Pedido no encontrado [X_X] ..."
            );
        }

        Pedido pedido = pedidoOpt.get();

        pedido.setEstado("Reembolsado");

        repository.save(pedido);

        return "[+] Pedido actualizado a REEMBOLSADO";
    }

}