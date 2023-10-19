/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controladores;

import com.example.demo.entidades.Noticia;
import com.example.demo.entidades.Usuario;
import com.example.demo.excepciones.MiExcepcion;
import com.example.demo.servicios.NoticiaServicio;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Max
 */
@Controller
@RequestMapping("/")
public class NoticiaControlador {
    
    @Autowired
    private NoticiaServicio noticiaServicio;
    
    @GetMapping("/")
    public String listar(ModelMap modelo){
        List<Noticia> noticias = noticiaServicio.listarNoticias();
        modelo.addAttribute("noticias", noticias);
        return "index.html";
    }
    
    @GetMapping("/login")
    public String ingresar(@RequestParam(required = false) String error, ModelMap modelo){
        if(error != null){
            modelo.put("error", "Usuario o contraseña invalidos");
        }
        return "login.html";
    }
    
    @GetMapping("/noticia/registrar")
    public String cargarNoticia(){
        return "panelAdmin.html";
    }
    
    @PostMapping("/noticia/registro")
    public String registro(@RequestParam String titulo,@RequestParam String cuerpo, ModelMap modelo){
        
        try {
            noticiaServicio.crearNoticia(titulo, cuerpo);
            modelo.put("Exito", "Chismecito registrado correctamente");
        } catch (MiExcepcion ex) {
            modelo.put("Error", ex.getMessage());
            return "panelAdmin.html";
        }
        return "panelAdmin.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session){
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
                
        return "inicio.html";
    }
    
    @GetMapping("/noticia/new/{id}")
    public String verNoticia(@PathVariable String id, ModelMap modelo){
        modelo.put("noticia", noticiaServicio.getOne(id));
        return "noticia.html";
    }
    
    @PostMapping("/noticia/new/{id}")
    public String verNoticia(@PathVariable String id){
        noticiaServicio.buscarNoticia(id);
        return "redirect:../actualidad";
    }
    
}
