package com.claujulian.libreria_api_egg.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutorRegistradoDTO {
    private Long id;
    private String nombre;
}