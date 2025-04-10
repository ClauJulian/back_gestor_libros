package com.claujulian.libreria_api_egg.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.claujulian.libreria_api_egg.entidades.Editorial;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial,Long> {

    @Query("SELECT e FROM Editorial e WHERE e.activo = true")
    List<Editorial> findByActivo();

}
