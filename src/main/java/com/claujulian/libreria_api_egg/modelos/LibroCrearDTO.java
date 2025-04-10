package com.claujulian.libreria_api_egg.modelos;

import lombok.Data;

@Data
public class LibroCrearDTO {
    private String titulo;
    private Integer ejemplares; 
    private Long id_autor;
    private Long id_editorial;
}
