package com.claujulian.libreria_api_egg.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LibroDTO {
    private Integer ejemplares;
    private Boolean activo;
    private String titulo; 
}
