package com.claujulian.libreria_api_egg.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.claujulian.libreria_api_egg.excepciones.MyExceptions;
import com.claujulian.libreria_api_egg.modelos.EditorialCrearDTO;
import com.claujulian.libreria_api_egg.modelos.EditorialRegistradaDTO;
import com.claujulian.libreria_api_egg.entidades.Editorial;
import com.claujulian.libreria_api_egg.entidades.Libro;
import com.claujulian.libreria_api_egg.repositorios.EditorialRepositorio;
import com.claujulian.libreria_api_egg.repositorios.LibroRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EditorialServicio {
    private final EditorialRepositorio editorialRepositorio;
    private final LibroRepositorio libroRepositorio;
 
     // EXTRAS
    private void validar(String nombre) throws MyExceptions {
        if (nombre.isEmpty() || nombre == null) {
            throw new MyExceptions("El nombre no puede ser nulo o estar vacio!");
        }
    }

    // CREATE
    @Transactional
    public EditorialRegistradaDTO crearEditorial(EditorialCrearDTO editorialCrearDTO) throws MyExceptions{
       
        validar(editorialCrearDTO.getNombre());
        Editorial editorial = new Editorial();
        editorial.setNombre(editorialCrearDTO.getNombre());
        editorial.setActivo(true);
        editorialRepositorio.save(editorial);

        EditorialRegistradaDTO editorialRegistrada = new EditorialRegistradaDTO(editorial.getId(),editorial.getNombre());
        return editorialRegistrada;
    }

    // READ
    @Transactional(readOnly = true)
    public List<Editorial> listarEditoriales() {
        List<Editorial> editoriales = new ArrayList<Editorial>();

        editoriales = editorialRepositorio.findAll();

        return editoriales;
    }

    // READ
    @Transactional(readOnly = true)
    public List<Editorial> listarEditorialesActivas() {
        List<Editorial> editoriales = new ArrayList<Editorial>();

        editoriales = editorialRepositorio.findByActivo();

        return editoriales;
    }

    // READ
     @Transactional(readOnly = true)
    public Editorial buscarPorID(Long id){
        return editorialRepositorio.getReferenceById(id);
    }

    // UPDATE
    @Transactional
    public void modificarEditorial(String nombre, Long id) throws MyExceptions{
        validar(nombre);
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            editorialRepositorio.save(editorial);
        }
    }

    // BAJA CON CAMBIO DE ESTADO
    @Transactional
    public void cambioEstadoEditorial(Long id) {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) { 
           Editorial editorial = respuesta.get();
            
            if(editorial.getActivo()==true){editorial.setActivo(false);
            } else editorial.setActivo(true);
           
           editorialRepositorio.save(editorial);
        }
    }

    // BAJA DEFINITIVA
    @Transactional
    public void bajaDefinitivaDeEditorial(Long id) {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) { 
           Editorial editorial = respuesta.get();
           List<Libro> libros = libroRepositorio.listarLibrosPorEditorial(editorial.getId());
           for (Libro libro : libros) {
            libroRepositorio.delete(libro);
           }
           editorialRepositorio.delete(editorial);
        }
    }

}
