package com.claujulian.libreria_api_egg.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.claujulian.libreria_api_egg.entidades.Autor;
import com.claujulian.libreria_api_egg.excepciones.MyExceptions;
import com.claujulian.libreria_api_egg.modelos.AutorCrearDTO;
import com.claujulian.libreria_api_egg.modelos.AutorDTO;
import com.claujulian.libreria_api_egg.modelos.AutorModifcarDTO;
import com.claujulian.libreria_api_egg.modelos.AutorRegistradoDTO;
import com.claujulian.libreria_api_egg.servicios.AutorServicio;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/autor")
public class AutorControlador {
    
    @Autowired
    private AutorServicio autorServicio;

    @PostMapping("/crear")
    public ResponseEntity<Object> crearAutor(@RequestBody AutorCrearDTO autorCrearDTO) throws MyExceptions{
           AutorRegistradoDTO autorRegistrado=autorServicio.crearAutor(autorCrearDTO);
            return new ResponseEntity<>(autorRegistrado, HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Autor>> listarAutores() {
        try {
            List<Autor> autores = autorServicio.listarAutores(); 
            return new ResponseEntity<>(autores,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<AutorDTO> listarAutores(@PathVariable Long id) {
        try {
            AutorDTO autorEntontrado = autorServicio.buscarPorID(id); 
            return new ResponseEntity<>(autorEntontrado,HttpStatus.OK);
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Error 404
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error 500
        }
    }

    @PatchMapping("/modificar")
    public ResponseEntity<Void> modificarAutor(@RequestBody AutorModifcarDTO autorModifcarDTO) {
        try {
            autorServicio.modificarAutor(autorModifcarDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/cambio_estado/{id}")
    public ResponseEntity<Void> cambioEstadoAutor(@PathVariable String id) {
        try {
            Long idAutor = Long.valueOf(id);
            autorServicio.cambioEstadoAutor(idAutor);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/baja_definitiva/{id}")
    public ResponseEntity<Void> bajaDefinitivaDeAutor(@PathVariable String id) {
        try {
            Long idAutor = Long.valueOf(id);
            autorServicio.bajaDefinitivaAutor(idAutor);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
