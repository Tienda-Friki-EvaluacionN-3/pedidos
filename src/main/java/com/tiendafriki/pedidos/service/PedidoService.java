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

    // Inyección de del repositorio

    @Autowired
    private PedidoRepository repository;

    // === (GET) LISTAR === //

    public List<Pedido> listar() {
        return repository.findAll();
    }

    public Pedido buscarPorId(Integer id) {

        // Retornamos el pedido buscado por id
        return repository.findById(id)

                // Pero en caso que no encuentre un pedido por esa id
                // devolvera eta excepcion del Manejadro de Errores:
                // NoSuchElementException: Error 404 no encontrado
                
                .orElseThrow(() ->
                new NoSuchElementException
                ( "[ERROR] Pedido no encontrado [X_X] ..."));
    }

    
    // === (GET) BUSCAR POR TELEFONO === //

    public List<Pedido> buscarPorTelefono(String telefono) {

    // Usamoe el telefono ingresado apra buscar por telefono en el repository
    // Los resultados los guardamos en una lista de pedidos que tengan ese mismo telefono

    List<Pedido> pedidos = repository.findBytelefono(telefono);

        // Si la lista esta vacia, quiere decir que no hay pedidos con ese telefono

        if (pedidos.isEmpty()) {

            throw new NoSuchElementException(
                "[ERROR] No existen pedidos asociados a ese telefono [X_X] ...");
        }

        // En caso contrario, retornamos la lista de pedidos con ese numero

        return pedidos;

    }

    // === (GET) BUSCAR POR DIRECCION === //
    
    public List<Pedido> buscarPorDireccion(String direccion) {

         List<Pedido> pedidos = repository.findByDireccion(direccion);

        if (pedidos.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] No existen pedidos asociados a esa direccion [X_X] ...");
        }

         return pedidos;
                
    }

    // === (POST) NUEVA FUNCION GUARDAR - CORREGIDA === //

    // Ahora hace las validaciones necesarias con carrito
    // Las funciones utilizadas se encuentran abajo:

    public String guardar(PedidoRequestDTO pedidoDTO) {

        // === VALIDAR CARRITO EXISTENTE === //

        // A la funcion validarCarrito(id) le damos la ID del carrito extradida del DTO de pedido ingresado
        // para comprobar si ese carrito existe

        validarCarrito(pedidoDTO.getCarritoId());

        // === VALIDAR PRODUCTOS === //

        // A la funcion validarProductosCarrito le damos el id y los detalles del carrito
        // extraidos del DTO del pedido ingresado por el usuario
        // para validar consistencia entre productos del carrito y el pedido asociado

        validarProductosCarrito(
                pedidoDTO.getCarritoId(),
                pedidoDTO.getDetalles()
        );

        // Luego de pasar las validaciones, se crea un nuevo objeto pedido

        Pedido pedido = new Pedido();

        // Le ponemos al pedido todos los datos extraidos  del DTO de Pedido

        pedido.setCarritoId(pedidoDTO.getCarritoId());

        pedido.setEmail(pedidoDTO.getEmail());

        pedido.setTelefono(pedidoDTO.getTelefono());

        pedido.setDireccion(pedidoDTO.getDireccion());

        pedido.setFechaRegistro(LocalDate.now()); // Ponemos la fecha actual

        pedido.setEstado("Creado"); // Cambiamos el estado simplemente a creado

        int total = 0; // El total por ahora quedara como cero antes de validar consistencia con carrito

        // Creamos una lista de detalles de pedido

        List<DetallePedido> detalles = new ArrayList<>();

        // Recorremos los detalles del pedido ingresado por el usuario

        for (DetallePedidoRequestDTO detalleDTO
                : pedidoDTO.getDetalles()) {

            // Creamos un nuevo objeto DetallePedido para insertar los datos

            DetallePedido detalle =
                    new DetallePedido();

            // Obtenemos los datos del DTO de detalle ingresado por el usuario
            // y los insertamos en el nuevo objeto detalle, por cada dato

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

            // Tambien calculamos el total obteniendo la cantidad y el precio de los detalles

            total += detalleDTO.getCantidad()
                    * detalleDTO.getPrecio();


            // Agregamos el detalle a la lista de detalles
            detalles.add(detalle);
        }

        // === AGREGAR IVA DEL 19% === //

        total = (int) (total * 1.19);

        // === NUEVO: VALIDAR TOTAL === //

        // Obtenemos el total del carrito a partir del ID de carrito del DTO de pedido

        Integer totalCarrito =
                obtenerTotalCarrito(
                        pedidoDTO.getCarritoId()
                );

        // Si el total es NO es igual al total de carrito

        if (!totalCarrito.equals(total)) {

            // Lanzamos un IllegalArgumentException, un mensaje de error de la lógica de negocio

            throw new IllegalArgumentException(
                    "[ERROR] El total del pedido no coincide con el carrito [X_X] ..."
            );
        }

        // En caso contrario, entonces insertamos el total y los detalles en el pedido

        pedido.setTotal(total);

        pedido.setDetalles(detalles);

        // Guardamos el pedido

        repository.save(pedido);

        return "[+] El pedido fue agregado correctamente";
    }

    // =============== FUNCIONES PARA VALIDACIONES DEL CARRITO ANTES DE CREAR PEDIDO ===================== //


        // NOTA: A continuación se veran muchos Try Catch y Rest Templates

        // REST TEMPLATE : Permite la comunicación HTTP con otros microservicios
        // al hacer una solicitud en uno de sus endpoints.
        // Ejemplo: Cuando Pedido solicita buscar id de Carrito para validar que existe

        // TRY CATCH: se utilizan porque la comunicación HTTP
        // con otros microservicios puede fallar.
        //
        // Ejemplos:
        // - El microservicio carrito está apagado
        // - La URL no existe
        // - Error de conexión
        // - Timeout
        //
        // Si ocurre un error durante la petición HTTP,
        // capturamos la excepción y lanzamos un mensaje controlado
        // para evitar errores internos no manejados.

        // Excepciones manejadas por el ManejadorErrores:

        // - IllegalArgumentException : Cualquier error de validaciondde logica de negocio que devuelva el service
        // - NoSuchElementException : Cualquier error de No Encontrado

        // Nota: RunTimeExcpetions es cualquier error inesperado al intentar comunicarse con otro microservicio

    // === NUEVO: VALIDAR EXISTENCIA DE CARRITO === //

    private void validarCarrito(Integer carritoId) {

        // Creamos un objeto Rest Template para la counicacion con carrito

        RestTemplate rt = new RestTemplate();

        // Guardamos la URL del endpoint de carrito para buscar carrito por id
        // le agregamos el id del carrito del pedido ingresado por el usuario

        String url =
                "http://localhost:8083/carrito/buscarxid/"
                        + carritoId;

        // Intentamos realizar la comunicacion

        try {

            // Rest Template obtendrá el objeto que devuelve la URL, es decir
            // el carrito que se busca por id

            rt.getForObject(url, Object.class);

            // Si el carrito no existe,
            // RestTemplate lanzará excepción automáticamente

        } catch (Exception e) {

            // Lanza oSuchElementException: No encontrado

            throw new NoSuchElementException(
                    "[ERROR] El carrito no existe [X_X] ..."
            );
        }
    }

    // === NUEVO: OBTENER TOTAL DEL CARRITO === //

    private Integer obtenerTotalCarrito(Integer carritoId) {

        RestTemplate rt = new RestTemplate();

        String url =
                "http://localhost:8083/carrito/total/"
                        + carritoId;

        try {

            Double total =
                    rt.getForObject(url, Double.class);

            // Devuelve el total como un número entero

            return total.intValue();

        } catch (Exception e) {

            // En caso de fallos con la comunicacion, lanzaráun RunTimeException:

            throw new RuntimeException(
                    "[ERROR] No se pudo obtener el total del carrito [X_X] ..."
            );
        }
    }

    // =============== FIN DE LAS FUNCIONES DE VALIDACIONES AL CARRITO PARA CREAR PEDIDO ===================== //
    

    // === VALIDAR PRODUCTOS DEL CARRITO === //

    // Con esta funcion, validaremos si los productos del detalle del pedido
    // Son los mismos del carrito indicado por id

    private void validarProductosCarrito(
            Integer carritoId,
            List<DetallePedidoRequestDTO> detallesDTO) {

        // Creamos el Rest Template para comunicarnos con carrito

        RestTemplate rt = new RestTemplate();
        
        // Guardamos la URL del endpoint de carrito que nos permite obtener los detalles
        // y le ingresamos el id del carrito ingresado por el usuario

        String url =
                "http://localhost:8083/detalleCarrito/carrito/"
                        + carritoId;

        // Intentamos realizar la comunicacion con carrito

        try {

            // Cremos una lista de Map para contener los detalles del carrito

            // Map<String, Object> = define una estructura de Clave : Objeto.
            // en este caso: "Producto" : producto

            List<Map<String, Object>> detallesCarrito =

                    // Ejecutamos el Res Template para que obtenga la lista de detalles del carrito usando la URL

                    rt.getForObject(url, List.class);

            // Recorrerá cada detalle ingresado por el usuario en el DTO de detalles
            // La variable dto representa cada detalle

            for (DetallePedidoRequestDTO dto : detallesDTO) {

                // La variable boolean existente contendrá verdadera si existe ese producto en el carrito

                boolean existe =

                        // Stream recorre la lista de detalles del carrito
                        detallesCarrito.stream()
                                // Comprueba si hay alguna coincidencia por cada detalles
                                .anyMatch(detalle ->

                                        // Para el detalle se obtiene la id de producto
                                        detalle.get("productoId")
                                                // Se comprueba si es equivalente al producto del DTO de detalle de pedido
                                                // obteniendo el producto por id
                                                .equals(dto.getProductoId())
                                );

                // Si existente NO es verdadero

                if (!existe) {

                    // Lnaza una IllegalArgumentException: Error de validacion de logica de negocio

                    throw new IllegalArgumentException(
                            "[ERROR] El producto "
                                    + dto.getProductoId()
                                    + " no existe en el carrito [X_X] ..."
                    );
                }
            }

        }

        // IMPORTANTE:
        // dejamos pasar el IllegalArgumentException original

        catch (IllegalArgumentException e) {

            throw e;
        }

        // Cualquier otro error:
        // conexión, microservicio apagado, etc, sera atrapado por RunTimeException

        catch (Exception e) {

            throw new RuntimeException(
                    "[ERROR] Error al validar productos del carrito [X_X] ..."
            );
        }
    }

    // === (PUT) NUEVA FUNCION ACTUALIZAR: === //

    // Ahora ingresamos tanto el DTO de actualizar como el id del pedido a actualizar
    // Usamos el DTO de actualizar para solo poder cambiar los datos permitidos

    public String actualizar(Integer id,PedidoActualizarDTO dto) {

        // Buscamos el pedido por id en rel repositorio
        // y guardamos el resultado en un contenedor Optional

        Optional<Pedido> pedidoOpt = repository.findById(id);

        // COMPROBAMOS EXISTENCIA:
        // Si el contenedor optional de pedido esta vacio,
        // significa que no existe

        if (pedidoOpt.isEmpty()) {

            // Lanza un NoSuchElementException: 404 no encontrado

            throw new NoSuchElementException(
                    "[ERROR] Pedido no encontrado [X_X] ..."
            );
        }
        
        // EN CASO CONTRARIO:

        // Creamos un nuevo objeto pedido a partir de los datos obtenidos del contenedor optional

        Pedido pedido = pedidoOpt.get();

        // SOLO se actualizan datos permitidos del objeto pedido
        // usando el DTO de actualizar: email, telefono y direccion

        pedido.setEmail(dto.getEmail());

        pedido.setTelefono(dto.getTelefono());

        pedido.setDireccion(dto.getDireccion());
        
        // Guardamos los cambios

        repository.save(pedido);

        return "[+] El pedido fue actualizado correctamente";
    }

    // === DELETE: ELIMINAR (Corregido) == //

    public String eliminar(Integer id) {

        // Buscamos el pedido que se desea eliminar por id en el repositorio
        // y el resultado lo guardamos en el contenedor Optional de pedido
        
        Optional<Pedido> pedidoOpt = repository.findById(id);

        // Si el contenedor optional de pedido esta vacio, significa que no existe el pedido indicado

        if (pedidoOpt.isEmpty()) {

            // Lanzamos excepcion de no encontrado

            throw new NoSuchElementException(
                    "[ERROR] Pedido no encontrado [X_X] ..."
            );
        }

        // Si existe, eliminamos

        repository.deleteById(id);

        return "[+] El pedido fue eliminado correctamente";

    }

    // NUEVO:

    // === (PUT): Marcar pedido como PAGADO: === //

    // Esta función se agregó para permitir que el pedido se marque automaticamnete
    // como pagado
    // cuando el el microservicio pago se efetua correctamente

    public String marcarComoPagado(Integer id) {

        // Se buscar el pedido por id (segun el id indicado por pago) y se guarda en una
        // variable:

        Optional<Pedido> pedidoOpt = repository.findById(id);

        // Se comprueba que el pedido exista:

        if (pedidoOpt.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] Pedido no encontrado [X_X] ..."
            );
        }

        // Se crear un objeto pedido nuevo a partir de los datos del pedido que se desea
        // actualizar

        Pedido pedido = pedidoOpt.get();

        // === NUEVO: DESCONTAR STOCK EN CATALOGO === //

        // Antes de marcar el pedido como pagado,
        // recorremos todos los detalles del pedido
        // para descontar stock de cada producto

        RestTemplate rt = new RestTemplate();

        // Recorremos cada detalle del pedido

        for (DetallePedido detalle : pedido.getDetalles()) {

            // URL del endpoint de catalogo encargado
            // de descontar stock automaticamente

            String url =
                    "http://localhost:8081/catalogo/descontarstock/"
                            + detalle.getProductoId()
                            + "/"
                            + detalle.getCantidad();

            try {

                // Ejecutamos PUT hacia catalogo
                // para descontar stock

                rt.put(url, null);

            } catch (Exception e) {

                // Si falla catalogo o el stock es insuficiente,
                // NO permitimos marcar el pedido como pagado

                throw new RuntimeException(
                        "[ERROR] No se pudo descontar stock del producto ID "
                                + detalle.getProductoId()
                                + " [X_X] ..."
                );
            }
        }

        // === FIN DESCUENTO DE STOCK === //

        // Se actualiza el estado del pedido a pagado

        pedido.setEstado("Pagado");

        // Se guarda el pedido actualizado

        repository.save(pedido);

        return "[+] Pedido actualizado a PAGADO";
    }

     // === (PUT): Marcar pedido como REEMBOLSADO: === //

    // Esta función se agregó para permitir que el pedido se marque automaticamnete como REEMBOLSADO
    // cuando el el microservicio devoluciones se efectue correctamente sobre pedido

    public String marcarComoReembolsado(Integer id){

        // Se busca el pedido por id (segun el id indicado por pago) y se guarda en una variable:

        Optional<Pedido> pedidoOpt = repository.findById(id);

        // Se comprueba que el pedido exista:

        if(pedidoOpt.isEmpty()){

            throw new NoSuchElementException(
                    "[ERROR] Pedido no encontrado [X_X] ..."
            );
        }

        //  Se crear un objeto pedido nuevo a partir de los datos del pedido que se desea actuaizar

        Pedido pedido = pedidoOpt.get();

        // Se actualiza el estado del pedido a reembolsado

        pedido.setEstado("Reembolsado");

        // Se guarda el pedido actualizado

        repository.save(pedido);

        return "[+] Pedido actualizado a REEMBOLSADO";
    }

}
