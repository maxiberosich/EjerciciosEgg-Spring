/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipo52.biblioteca.controladores;

import com.equipo52.biblioteca.entidades.Autor;
import com.equipo52.biblioteca.entidades.Editorial;
import com.equipo52.biblioteca.excepciones.MiExcepcion;
import com.equipo52.biblioteca.servicios.AutorServicio;
import com.equipo52.biblioteca.servicios.EditorialServicio;
import com.equipo52.biblioteca.servicios.LibroServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Max
 */
@Controller
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/cargar")
    public String cargar(ModelMap modelo) {
        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
        
        return "libro_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required=false) Long isbn, @RequestParam String titulo,
            @RequestParam(required=false) Integer ejemplares, ModelMap modelo) {
        try {
            libroServicio.crearLibro(isbn, titulo, ejemplares, titulo, titulo);
            modelo.put("exito", "El libro fue cargado exitosamente!");
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            return "libro_form.html";
        }
        return "index.html";
    }

}
