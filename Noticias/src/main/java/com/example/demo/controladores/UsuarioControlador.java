/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controladores;

import com.example.demo.entidades.Usuario;
import com.example.demo.excepciones.MiExcepcion;
import com.example.demo.servicios.UsuarioServicio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Max
 */
@Controller
@RequestMapping("/user")
public class UsuarioControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @GetMapping("/registrar")
    public String registrar(){
        return "registrar.html";
    }
    
    @PostMapping("/registro")
    public String registro(MultipartFile archivo, @RequestParam String nombre, @RequestParam String password, @RequestParam String password2, @RequestParam String email, ModelMap modelo){
        try {
            usuarioServicio.registrar(archivo,nombre, email, password, password2);
            modelo.put("exito", "Usuario registrado correctamente.");
            return "index.html";
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "registrar.html";
        }
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        
        modelo.put("usuario", usuario);
        
        return "usuario_modificar.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @PostMapping("/perfil/{id}")
    public String actualizar(MultipartFile archivo, @PathVariable String id, @RequestParam String nombre, @RequestParam String email, @RequestParam String password, @RequestParam String password2, ModelMap modelo){
        try {
            usuarioServicio.actualizar(archivo, id, nombre, email, password, password2);
            modelo.put("exito", "Usuario actualizado correctamente");
            return "inicio.html";
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            
            return "usuario_modificar.html";
        }
    }
}
