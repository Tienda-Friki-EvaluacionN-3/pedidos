package com.tiendafriki.pedidos.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
public class ErrorDTO {

    private LocalDateTime timestamp;
    private int status;
    private String mensaje;
    private Map<String, String> errores;
    private String path;

        public ErrorDTO(LocalDateTime timestamp, int status, String mensajes, Map<String, String> errores, String path){
        this.timestamp = timestamp;
        this.status = status;
        this.mensaje = mensajes;
        this.errores = errores;
        this.path = path;
    }
}
