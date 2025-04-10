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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.claujulian.libreria_api_egg.entidades.Editorial;
import com.claujulian.libreria_api_egg.excepciones.MyExceptions;
import com.claujulian.libreria_api_egg.modelos.EditorialCrearDTO;
import com.claujulian.libreria_api_egg.modelos.EditorialRegistradaDTO;
import com.claujulian.libreria_api_egg.servicios.EditorialServicio;


@RestController
@RequestMapping("/editorial")
public class EditorialControlador {
    
    @Autowired
    private EditorialServicio editorialServicio;

    @PostMapping("/crear")
    public ResponseEntity<Object> crearEditorial(@RequestBody EditorialCrearDTO editorialCrearDTO) throws MyExceptions{
        
            EditorialRegistradaDTO editorialRegistrada = editorialServicio.crearEditorial(editorialCrearDTO);
            return new ResponseEntity<>(editorialRegistrada, HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Editorial>> listarEditoriales() {
        try {
            List<Editorial> editoriales = editorialServicio.listarEditoriales(); 
            return new ResponseEntity<>(editoriales,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar_activas")
    public ResponseEntity<List<Editorial>> listarEditorialesActivas() {
        try {
            List<Editorial> editoriales = editorialServicio.listarEditorialesActivas(); 
            return new ResponseEntity<>(editoriales,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/modificar")
    public ResponseEntity<Void> modificarEditorial(@RequestParam String nombre, @RequestParam String id) {
        try {
            Long idEditorial = Long.valueOf(id);
            editorialServicio.modificarEditorial(nombre, idEditorial);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/cambio_estado/{id}")
    public ResponseEntity<Void> cambioEstadoEditorial(@PathVariable String id) {
        try {
            Long idEditorial = Long.valueOf(id);
            editorialServicio.cambioEstadoEditorial(idEditorial);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/baja_definitiva/{id}")
    public ResponseEntity<Void> bajaDefinitivaDeEditorial(@PathVariable String id) {
        try {
            Long idEditorial = Long.valueOf(id);
            editorialServicio.bajaDefinitivaDeEditorial(idEditorial);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
