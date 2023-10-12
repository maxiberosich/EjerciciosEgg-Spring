/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipo52.biblioteca.servicios;

import com.equipo52.biblioteca.entidades.Autor;
import com.equipo52.biblioteca.entidades.Editorial;
import com.equipo52.biblioteca.entidades.Libro;
import com.equipo52.biblioteca.excepciones.MiExcepcion;
import com.equipo52.biblioteca.repositorios.AutorRepositorio;
import com.equipo52.biblioteca.repositorios.EditorialRepositorio;
import com.equipo52.biblioteca.repositorios.LibroRepositorio;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Max
 */
@Service
public class LibroServicio {
    
    @Autowired
    private LibroRepositorio libroRepositorio;    
    @Autowired
    private AutorRepositorio autorRepositorio;    
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiExcepcion{
        
        validar(isbn, titulo, ejemplares, idAutor, idEditorial);
        
        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();
        Libro libro = new Libro();
        
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);        
        libro.setAlta(new Date());
        
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        
        
        libroRepositorio.save(libro);
    }
    
    public List<Libro> listarLibros(){
        
        List<Libro> libros = new ArrayList<>();
        
        libros = libroRepositorio.findAll();
        
        return libros;
        
    }
    
    public void modificarLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiExcepcion{
        
        validar(isbn, titulo, ejemplares, idAutor, idEditorial);
        
        Optional<Libro> respuesta = libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);
        
        Autor autor = new Autor();
        Editorial editorial = new Editorial();
        
        if(respuestaAutor.isPresent()){
            autor = respuestaAutor.get();
        }
        
        if(respuestaEditorial.isPresent()){
            editorial = respuestaEditorial.get();
        }
        
        if(respuesta.isPresent()){
            Libro libro = respuesta.get();
            
            libro.setTitulo(titulo);
            libro.setEjemplares(ejemplares);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            
            libroRepositorio.save(libro);
        }        
    }
    
    public Libro getOne(Long isbn){
        return libroRepositorio.getOne(isbn);
    }
    
    private void validar(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiExcepcion{
        
        if(isbn == null){
            throw new MiExcepcion("El isbn no puede ser nulo");
        }
        
        if(titulo == null || titulo.isEmpty()){
            throw new MiExcepcion("El titulo no puede ser nulo o estar en blanco");
        }
        
        if(ejemplares == null){
            throw new MiExcepcion("Los ejemplares no pueden ser nulos");
        }
        
        if(idAutor == null || idAutor.isEmpty()){
            throw new MiExcepcion("El id del Autor no puede ser nulo o estar en blanco");
        }
        
        if(idEditorial == null || idEditorial.isEmpty()){
            throw new MiExcepcion("El id de la Editorial no puede ser nulo o estar en blanco");
        }
        
    }   
    
}
