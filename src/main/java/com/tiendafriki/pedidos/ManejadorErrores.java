package com.tiendafriki.pedidos;

import com.tiendafriki.pedidos.dto.ErrorDTO;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import java.time.*;
import java.util.*;

// Este es el Manejador de Errores global.

// Servirá para centralizar los errores y status http de:
// 1- Validaciones jakarta (MethodArgumentNotValidException)
// 2- Errores de validacion de logica de negocio en el service (IllegalArgumentException)
// 3 -Errores de No Encontrado (NoSuchElementException)
// 4 - Cualquier otro error interno con el servidor y la comunicacion entre microservicios (RuntimeException)

@RestControllerAdvice
public class ManejadorErrores {

    // === ERROR 400: VALIDACIONES JAKARTA - MALA SOLICITUD === //

    // Esta excepción ocurre automáticamente cuando fallan
    // las validaciones Jakarta del DTO.
    //
    // Ejemplos:
    // - Campos vacíos
    // - Correos inválidos
    // - Números negativos
    // - Strings demasiado largos
    //
    // Spring Boot detecta automáticamente los errores
    // antes de entrar al service.

    @ExceptionHandler(MethodArgumentNotValidException.class)

    public ResponseEntity<ErrorDTO> manejarErroresValidacion(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                400,
                "[ERROR] Solicitud Incorrecta [X_X]",
                errores,
                request.getRequestURI());

        return ResponseEntity.badRequest().body(errorDTO);

    }

    // === ERROR 404: NO ENCONTRADO === //

    // Esta excepción se utiliza cuando el recurso solicitado
    // no existe en la base de datos o en otro microservicio.
    //
    // Ejemplos:
    // - Pedido no encontrado
    // - Carrito inexistente
    // - Producto inexistente
    //
    // El service o controller lanzan esta excepción usando "throw"
    // y el manejador transforma automáticamente el error
    // en una respuesta HTTP 404 (Not Found).

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorDTO> manejarErroresNoEncontrado(
            NoSuchElementException ex,
            HttpServletRequest request) {

        Map<String, String> errores = new HashMap<>();
        errores.put("error", ex.getMessage());

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                404,
                "[ERROR] Recurso No Encontrado [X_X]",
                errores,
                request.getRequestURI());

        return ResponseEntity.status(404).body(errorDTO);

    }

    // === ERRORES DE VALIDACIONES DE NEGOCIO === //

    // Esta excepción se utiliza para errores de lógica de negocio.
    // A diferencia de las validaciones Jakarta (@NotNull, @Email, etc),
    // estas validaciones dependen de reglas internas del sistema.
    //
    // Ejemplos:
    // - El total del pedido no coincide con el carrito
    // - Un producto no existe dentro del carrito
    // - El carrito no cumple una condición requerida
    //
    // El service lanza la excepción usando "throw" con su correspondiente mensaje
    // y este manejador se encarga de convertirla automáticamente
    // en una respuesta HTTP 400 (Bad Request).
    //
    // Gracias a esto:
    //
    // - El controller queda más limpio
    // - No es necesario devolver manualmente mensajes de error
    // - La lógica de errores queda centralizada

    @ExceptionHandler(IllegalArgumentException.class)

    public ResponseEntity<ErrorDTO> ErrorSolicitud(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        ErrorDTO error = new ErrorDTO(

                LocalDateTime.now(),

                400,

                // MOSTRAMOS EL MENSAJE REAL
                // enviado desde el service

                ex.getMessage(),

                null,

                request.getRequestURI());

        return ResponseEntity.badRequest().body(error);
    }
    

    // === ERROR 500: ERROR INTERNO DEL SERVIDOR === //

        // Esta excepción se utiliza para errores inesperados
        // ocurridos durante la ejecución del sistema.
        //
        // Generalmente ocurre cuando existe un problema
        // al comunicarse con otros microservicios.
        //
        // Ejemplos:
        //
        // - El microservicio carrito está apagado
        // - Falló la conexión HTTP
        // - Timeout de comunicación
        // - URL inexistente
        // - Error inesperado del servidor
        //
        // Normalmente estas excepciones ocurren dentro
        // de bloques try-catch del service.
        //
        // Cuando ocurre uno de estos errores,
        // se lanza un RuntimeException y este manejador
        // lo transforma automáticamente en un
        // HTTP 500 (Internal Server Error).

        @ExceptionHandler(RuntimeException.class)

        public ResponseEntity<ErrorDTO> manejarErrorInterno(
                RuntimeException ex,
                HttpServletRequest request) {

        Map<String, String> errores = new HashMap<>();

        errores.put("error", ex.getMessage());

        ErrorDTO errorDTO = new ErrorDTO(

                LocalDateTime.now(),

                500,

                "[ERROR] Error Interno del Servidor [X_X]",

                errores,

                request.getRequestURI());

        return ResponseEntity.status(500).body(errorDTO);
        }


}
