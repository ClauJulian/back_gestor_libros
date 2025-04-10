package com.claujulian.libreria_api_egg.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LibroRegistradoDTO {
    private Long id;
    private String titulo;
    private Integer ejemplares; 
    private Long id_autor;
    private Long id_editorial;
}
