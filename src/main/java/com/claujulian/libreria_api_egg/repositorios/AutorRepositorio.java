package com.claujulian.libreria_api_egg.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.claujulian.libreria_api_egg.entidades.Autor;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, Long>{

}
