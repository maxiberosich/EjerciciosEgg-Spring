/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.servicios;

import com.example.demo.entidades.Imagen;
import com.example.demo.entidades.Usuario;
import com.example.demo.enums1.Rol;
import com.example.demo.excepciones.MiExcepcion;
import com.example.demo.repositorios.UsuarioRepositorio;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Max
 */
@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void registrar(MultipartFile archivo, String nombre, String email, String password, String password2) throws MiExcepcion {

        validar(nombre, email, password, password2);

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setActivo(Boolean.TRUE);
        usuario.setRol(Rol.USER);

        Imagen imagen = imagenServicio.guardar(archivo);

        usuario.setImagen(imagen);

        usuarioRepositorio.save(usuario);

    }
    
    @Transactional
    public void actualizar(MultipartFile archivo, String idUsuario, String nombre, String email, String password, String password2) throws MiExcepcion{
        validar(nombre,email,password, password2);
        
        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);
        if(respuesta.isPresent()){
            Usuario usuario = respuesta.get();
            
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
            
            usuario.setRol(Rol.USER);
            
            String idImagen = null;
            
            if(usuario.getImagen() != null){
                idImagen = usuario.getImagen().getId();
            }
            
            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
            
            usuario.setImagen(imagen);
            
            usuarioRepositorio.save(usuario);
        }
    }
    
    public Usuario getOne(String id){
        return usuarioRepositorio.getOne(id);
    }

    private void validar(String nombre, String email, String password, String password2) throws MiExcepcion {
        if (nombre.isEmpty() || nombre == null) {
            throw new MiExcepcion("El nombre no puede ser nulo o estar vacio");
        }
        if (email.isEmpty() || email == null) {
            throw new MiExcepcion("El email no puede ser nulo o estar vacio");
        }
        if (password.isEmpty() || password == null || password.length() < 6) {
            throw new MiExcepcion("La contraseña debe tener como minimo 6 caracteres");
        }
        if (!password.equals(password2)) {
            throw new MiExcepcion("Las contraseñas ingresadas deben ser iguales");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);

        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", usuario);

            return new User(usuario.getEmail(),usuario.getPassword(),permisos);
        } else {
            return null;
        }
    }

}
