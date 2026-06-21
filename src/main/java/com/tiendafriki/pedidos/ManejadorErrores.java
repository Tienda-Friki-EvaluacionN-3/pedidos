package com.tiendafriki.pedidos;

import com.tiendafriki.pedidos.dto.ErrorDTO;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import java.time.*;
import java.util.*;

@RestControllerAdvice
public class ManejadorErrores {

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

    @ExceptionHandler(IllegalArgumentException.class)

    public ResponseEntity<ErrorDTO> ErrorSolicitud(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        ErrorDTO error = new ErrorDTO(

                LocalDateTime.now(),

                400,

                ex.getMessage(),

                null,

                request.getRequestURI());

        return ResponseEntity.badRequest().body(error);
    }

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