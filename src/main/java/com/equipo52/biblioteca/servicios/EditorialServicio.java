/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipo52.biblioteca.servicios;

import com.equipo52.biblioteca.entidades.Editorial;
import com.equipo52.biblioteca.excepciones.MiExcepcion;
import com.equipo52.biblioteca.repositorios.EditorialRepositorio;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Max
 */
@Service
public class EditorialServicio {
    
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Transactional
    public void crearEditorial(String nombre) throws MiExcepcion{
        
        validar(nombre);
        
        Editorial editorial = new Editorial();
        
        editorial.setNombre(nombre);
        
        editorialRepositorio.save(editorial);
        
    }
    
    public List<Editorial> listarEditoriales(){
        
        List<Editorial> editoriales = new ArrayList<>();
        
        editoriales = editorialRepositorio.findAll();
        
        return editoriales;
        
    }
    
    public void modificarEditorial(String id, String nombre) throws MiExcepcion{
        
        validar(nombre);
        
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        
        if(respuesta.isPresent()){
            
            Editorial editorial = respuesta.get();
            
            editorial.setNombre(nombre);
            
            editorialRepositorio.save(editorial);
            
        }
        
    }
    
    private void validar(String nombre) throws MiExcepcion{
        if (nombre == null || nombre.isEmpty()) {
            throw new MiExcepcion("El nombre no puede ser nulo o estar vacio.");
        }
    }
    
}
