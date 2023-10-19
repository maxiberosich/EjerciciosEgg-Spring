/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.servicios;

import com.example.demo.entidades.Imagen;
import com.example.demo.excepciones.MiExcepcion;
import com.example.demo.repositorios.ImagenRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Max
 */
@Service
public class ImagenServicio {
    
    @Autowired
    private ImagenRepositorio imagenRepositorio;
    
    public Imagen guardar(MultipartFile archivo) throws MiExcepcion{
        if(archivo != null){            
            try {
                Imagen imagen = new Imagen();
            
                imagen.setNombre(archivo.getName());
                imagen.setMime(archivo.getContentType());
                imagen.setContenido(archivo.getBytes());
                
                return imagenRepositorio.save(imagen);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }            
        }
        return null;
    }
    
    public Imagen actualizar(MultipartFile archivo, String idImagen){
        if(archivo != null){
            try {
                Imagen imagen = new Imagen();
                
                if(idImagen != null){
                    Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);
                    
                    if(respuesta.isPresent()){
                        imagen = respuesta.get();
                    }
                }
                
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                
                return imagenRepositorio.save(imagen);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
    
    
    
}
