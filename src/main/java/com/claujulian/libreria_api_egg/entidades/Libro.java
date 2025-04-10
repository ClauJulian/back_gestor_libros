package com.claujulian.libreria_api_egg.entidades;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name="libros")
@Data
@Builder
public class Libro {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer ejemplares;
    private Boolean activo;
    private String titulo; 
    
    @ManyToOne
    @JoinColumn(name="id_autor")
    private Autor autor;

    @ManyToOne
    @JoinColumn(name="id_editorial")
    private Editorial editorial;

    public Libro() {
    }

    public Libro(Long id, Integer ejemplares, Boolean activo, String titulo, Autor autor, Editorial editorial) {
        this.id = id;
        this.ejemplares = ejemplares;
        this.activo = activo;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
    }

    
}
