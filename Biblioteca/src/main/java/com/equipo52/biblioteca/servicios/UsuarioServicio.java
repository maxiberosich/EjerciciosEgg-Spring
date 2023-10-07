/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipo52.biblioteca.servicios;

import com.equipo52.biblioteca.entidades.Usuario;
import com.equipo52.biblioteca.enumeraciones.Rol;
import com.equipo52.biblioteca.excepciones.MiExcepcion;
import com.equipo52.biblioteca.repositorios.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Max
 */
@Service
public class UsuarioServicio implements UserDetailsService{
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Transactional
    public void registrar(String nombre, String email, String password, String password2) throws MiExcepcion{
        
        validar(nombre, email, password, password2);
        
        Usuario usuario = new Usuario();
        
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        
        usuario.setRol(Rol.USER);
        
        usuarioRepositorio.save(usuario);
        
    }
    
    private void validar(String nombre, String email, String password, String password2) throws MiExcepcion{
        
        if(nombre.isEmpty() || nombre == null){
            throw new MiExcepcion("El nombre no puede ser nulo o estar vacio");
        }
        if(email.isEmpty() || email == null){
            throw new MiExcepcion("El email no puede ser nulo o estar vacio");
        }
        if(password.isEmpty() || password == null || password.length() < 4){
            throw new MiExcepcion("La contraseña no puede estar vacia, y debe tener mas de 3 caracteres");
        }
        if(!password.equals(password2)){
            throw new MiExcepcion("Las contraseñas ingresadas deben ser iguales");
        }
        
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
        
        if(usuario != null){
            List<GrantedAuthority> permisos = new ArrayList();
            
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            
            permisos.add(p);
            
            return new User(usuario.getEmail(), usuario.getPassword(),permisos);
        }else{
            return null;
        }
    }
    
}
