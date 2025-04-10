package com.claujulian.libreria_api_egg.controladores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.claujulian.libreria_api_egg.entidades.Libro;
import com.claujulian.libreria_api_egg.excepciones.MyExceptions;
import com.claujulian.libreria_api_egg.modelos.LibroCrearDTO;
import com.claujulian.libreria_api_egg.modelos.LibroDTO;
import com.claujulian.libreria_api_egg.modelos.LibroRegistradoDTO;
import com.claujulian.libreria_api_egg.servicios.LibroServicio;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/libro")
@RequiredArgsConstructor
public class LibroControlador {

    private final LibroServicio libroServicio;

    @PostMapping("/crear")
    public ResponseEntity<Object> crearLibro(@RequestBody LibroCrearDTO libroCrearDTO) throws MyExceptions{
       
            LibroRegistradoDTO libroRegistrado = libroServicio.crearLibro(libroCrearDTO);
            return new ResponseEntity<>(libroRegistrado,HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<Object> listarLibros() {
        try {
            List<Libro> libros = libroServicio.listarLibros(); 
            return new ResponseEntity<>(libros, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Object> listarLibros(@PathVariable Long id) {
        try {
            LibroDTO libroEncontrado= libroServicio.buscarPorId(id); 
            return new ResponseEntity<>(libroEncontrado, HttpStatus.OK);
        } catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Error 404
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error 500
        }
    }

    @PatchMapping("/modificar")
    public ResponseEntity<Void> modificarLibro(@RequestParam String id, @RequestParam String titulo, @RequestParam String ejemplares, @RequestParam String idAutor, @RequestParam String idEditorial   ) {
        try {
            
            libroServicio.modificarLibro(Long.valueOf(id), titulo, Integer.valueOf(ejemplares), Long.valueOf(idAutor), Long.valueOf(idEditorial));
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/cambio_estado/{id}")
    public ResponseEntity<Void> cambioEstadoLibro(@PathVariable String id) {
        try {
            Long idLibro = Long.valueOf(id);
            libroServicio.cambioEstadoLibro(idLibro);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
