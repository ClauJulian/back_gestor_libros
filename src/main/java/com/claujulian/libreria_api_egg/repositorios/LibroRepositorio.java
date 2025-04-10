package com.claujulian.libreria_api_egg.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.claujulian.libreria_api_egg.entidades.Libro;
import java.util.List;


@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Long>{

    @Query("SELECT l FROM Libro l WHERE l.autor.id = :id")
    List<Libro> listarLibrosPorAutor(Long id);

    @Query("SELECT l FROM Libro l WHERE l.editorial.id = :id")
    List<Libro> listarLibrosPorEditorial(Long id);
}
