/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.servicios;

import com.example.demo.entidades.Imagen;
import com.example.demo.excepciones.MiExcepcion;
import com.example.demo.repositorios.ImagenRepositorio;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    
    
}
