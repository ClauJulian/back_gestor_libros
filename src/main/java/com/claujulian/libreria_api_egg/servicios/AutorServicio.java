package com.claujulian.libreria_api_egg.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.claujulian.libreria_api_egg.entidades.Autor;
import com.claujulian.libreria_api_egg.entidades.Libro;
import com.claujulian.libreria_api_egg.excepciones.MyExceptions;
import com.claujulian.libreria_api_egg.modelos.AutorCrearDTO;
import com.claujulian.libreria_api_egg.modelos.AutorDTO;
import com.claujulian.libreria_api_egg.modelos.AutorModifcarDTO;
import com.claujulian.libreria_api_egg.modelos.AutorRegistradoDTO;
import com.claujulian.libreria_api_egg.repositorios.AutorRepositorio;
import com.claujulian.libreria_api_egg.repositorios.LibroRepositorio;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AutorServicio {

    private final AutorRepositorio autorRepositorio;
    private final LibroRepositorio libroRepositorio;

     // EXTRAS
    private void validar(String nombre) throws MyExceptions {
        if (nombre.isEmpty() || nombre == null) {
            throw new MyExceptions("El nombre no puede ser nulo o estar vacio!");
        }
    }

    // CREATE
    @Transactional
    public AutorRegistradoDTO crearAutor(AutorCrearDTO autorCrearDTO) throws MyExceptions {
        validar(autorCrearDTO.getNombre());
        Autor autor = new Autor();

        autor.setActivo(true);
        autor.setNombre(autorCrearDTO.getNombre());
        autorRepositorio.save(autor);

        AutorRegistradoDTO autorCreado = new AutorRegistradoDTO(autor.getId(),autor.getNombre());
        return autorCreado;
    }

    // READ
    @Transactional(readOnly = true)
    public List<Autor> listarAutores() {
        List<Autor> autores = new ArrayList<Autor>();

        autores = autorRepositorio.findAll();

        return autores;
    }


    // READ
    @Transactional(readOnly = true)
    public AutorDTO buscarPorID(Long id){

        Optional<Autor> autorBuscado = autorRepositorio.findById(id);
        AutorDTO autorEncontrado = null;
        try{
            if(autorBuscado.isPresent()){
                autorEncontrado = new AutorDTO(autorBuscado.get().getId(), autorBuscado.get().getNombre());                
            } else {
                throw new EntityNotFoundException("No se encontró el Autor"); // Error 404
            }
        }catch(EntityNotFoundException e){
            throw e;
        }
        
        return autorEncontrado;
    }


    // UPDATE
    @Transactional
    public void modificarAutor(AutorModifcarDTO autorModificarDTO) throws MyExceptions {
        validar(autorModificarDTO.getNombre());
        Optional<Autor> respuesta = autorRepositorio.findById(autorModificarDTO.getId());
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setNombre(autorModificarDTO.getNombre());
            autorRepositorio.save(autor);
        }
    }

    // BAJA CAMBIO DE ESTADO
    @Transactional
     public void cambioEstadoAutor(Long id) {
         Optional<Autor> respuesta = autorRepositorio.findById(id);
         if (respuesta.isPresent()) { 
             Autor autor = respuesta.get();
            
             if(autor.getActivo()==true){autor.setActivo(false);
             }else autor.setActivo(true);
             
             autorRepositorio.save(autor);
         }
     }

    // BAJA DEFINITIVA
    @Transactional
    public void bajaDefinitivaAutor(Long id) {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) { 
            Autor autor = respuesta.get();
            List<Libro> libros = libroRepositorio.listarLibrosPorAutor(autor.getId());
            for (Libro libro : libros) {
                libroRepositorio.delete(libro);
            }           
            autorRepositorio.delete(autor);
        }
    }
}
