package com.tiendafriki.pedidos;

import com.tiendafriki.pedidos.dto.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
                "Solicitud Incorrecta",
                errores,
                request.getRequestURI());

        return ResponseEntity.badRequest().body(errorDTO);

    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDTO> manejarErrores(
            RuntimeException ex,
            HttpServletRequest request) {

        Map<String, String> errores = new HashMap<>();
        errores.put("error", ex.getMessage());

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                404,
                "No Encontrado",
                errores,
                request.getRequestURI());

        return ResponseEntity.status(404).body(errorDTO);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> manejarErrorInterno(
            Exception ex,
            HttpServletRequest request) {

        Map<String, String> errores = new HashMap<>();
        errores.put("error", ex.getMessage());

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                500,
                "Error Interno del Servidor",
                errores,
                request.getRequestURI());

        return ResponseEntity.status(500).body(errorDTO);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDTO> manejarErroresBaseDatos(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                400,
                "El email ya esta registrado",
                null,
                request.getRequestURI());
        return ResponseEntity.badRequest().body(errorDTO);
    }

}
