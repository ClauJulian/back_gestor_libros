package com.claujulian.libreria_api_egg.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.claujulian.libreria_api_egg.excepciones.MyExceptions;
import com.claujulian.libreria_api_egg.modelos.LibroCrearDTO;
import com.claujulian.libreria_api_egg.modelos.LibroDTO;
import com.claujulian.libreria_api_egg.modelos.LibroRegistradoDTO;
import com.claujulian.libreria_api_egg.entidades.Autor;
import com.claujulian.libreria_api_egg.entidades.Editorial;
import com.claujulian.libreria_api_egg.entidades.Libro;
import com.claujulian.libreria_api_egg.repositorios.AutorRepositorio;
import com.claujulian.libreria_api_egg.repositorios.EditorialRepositorio;
import com.claujulian.libreria_api_egg.repositorios.LibroRepositorio;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LibroServicio {

    private final LibroRepositorio libroRepositorio;
    private final AutorRepositorio autorRepositorio;
    private final EditorialRepositorio editorialRepositorio;


     // EXTRAS
    private void validar(String titulo,Long idAutor, Long idEditorial) throws MyExceptions{
        if (titulo.isEmpty() || titulo == null) {
            throw new MyExceptions("El titulo no puede estar vacio o ser nulo!");
        }
        if (idAutor == null) {
            throw new MyExceptions("Debes proveer un id de Autor!");
        }
        if (idEditorial == null) {
            throw new MyExceptions("Debes proveer un id de Editorial!");
        }
    }

    // CREATE 
    @Transactional
    public LibroRegistradoDTO crearLibro(LibroCrearDTO libroCrearDTO) throws MyExceptions {
        validar(libroCrearDTO.getTitulo(), libroCrearDTO.getId_autor(), libroCrearDTO.getId_editorial());
       
        Optional<Autor> autorDTO = autorRepositorio.findById(libroCrearDTO.getId_autor());
        Optional<Editorial> editorialDTO = editorialRepositorio.findById(libroCrearDTO.getId_editorial());
        if(autorDTO.isPresent() && editorialDTO.isPresent()){
            Autor autor = autorDTO.get();
            Editorial editorial = editorialDTO.get();
        
            Libro libro = Libro.builder()
                .titulo(libroCrearDTO.getTitulo())
                .ejemplares(libroCrearDTO.getEjemplares())
                .autor(autor)
                .editorial(editorial)
                .activo(true)
                .build();

        libro = libroRepositorio.save(libro);
        LibroRegistradoDTO libroRegistrado = new LibroRegistradoDTO(libro.getId(), libro.getTitulo(),libro.getEjemplares(),libro.getAutor().getId(), libro.getEditorial().getId());
        return libroRegistrado;} else {
            throw new  MyExceptions("El autor y la editorial deben ser válidos.");
        }
    }

    // READ
    @Transactional(readOnly = true)
    public List<Libro> listarLibros() {
        List<Libro> libros = new ArrayList<Libro>();
        libros = libroRepositorio.findAll();
        return libros;
    }

    // READ
    @Transactional(readOnly = true)
    public LibroDTO buscarPorId(Long id){

        Optional<Libro> libroBuscado = libroRepositorio.findById(id);
        LibroDTO libroEncontrado = null;

        try{

            if(libroBuscado.isPresent()){
                libroEncontrado = new LibroDTO(libroBuscado.get().getEjemplares(),libroBuscado.get().getActivo(), libroBuscado.get().getTitulo());
            }else {
                throw new EntityNotFoundException("No se encontró el Autor"); // Error 404
            }
        }catch(EntityNotFoundException e){
            throw e;
        }
        return libroEncontrado;
    }

    // UPDATE

    @Transactional
    public void modificarLibro(Long idLibro, String titulo, Integer ejemplares, Long idAutor, Long idEditorial) throws MyExceptions {
        validar(titulo, idAutor, idEditorial);
        Optional<Libro> respuestaLibro = libroRepositorio.findById(idLibro);

        if (respuestaLibro.isPresent()) {
            Libro libro = respuestaLibro.get();
            Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
            Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);

            libro.setTitulo(titulo);
            libro.setEjemplares(ejemplares);
            if (respuestaAutor.isPresent()) {
                libro.setAutor(respuestaAutor.get());
            }
            if (respuestaEditorial.isPresent()) {
                libro.setEditorial(respuestaEditorial.get());
            }

            libroRepositorio.save(libro);
        }}

    // BAJA_ESTADO_INACTIVO
    @Transactional
        public void cambioEstadoLibro(Long id) {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) { 
           Libro libro = respuesta.get();

           if(libro.getActivo()==true){
                libro.setActivo(false);
            } else libro.setActivo(true);

           libroRepositorio.save(libro);
        }
        
    }

    



}
