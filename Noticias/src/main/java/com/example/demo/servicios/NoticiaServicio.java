/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.servicios;

import com.example.demo.entidades.Noticia;
import com.example.demo.excepciones.MiExcepcion;
import com.example.demo.repositorios.NoticiaRepositorio;
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
public class NoticiaServicio {
    
    @Autowired
    private NoticiaRepositorio noticiaRepositorio;
    
    @Transactional
    public void crearNoticia(String titulo, String cuerpo) throws MiExcepcion{
        verificar(titulo,cuerpo);
        
        Noticia noticia = new Noticia();
        noticia.setTitulo(titulo);
        noticia.setCuerpo(cuerpo);
        noticiaRepositorio.save(noticia);
    }
    
    @Transactional
    public List<Noticia> listarNoticias(){
        List<Noticia> noticias = new ArrayList();
        noticias = noticiaRepositorio.findAll();
        return noticias;
    }
    
    @Transactional
    public Noticia buscarNoticia(String id){
        Optional<Noticia> noticiaElegida = noticiaRepositorio.findById(id);
        Noticia noti = noticiaElegida.get();
        return noti;
    }
    
    public Noticia getOne(String id){
        return noticiaRepositorio.getOne(id);
    }
    
    private void verificar(String titulo,String cuerpo) throws MiExcepcion{
        if(titulo == null || titulo.isEmpty()){
            throw new MiExcepcion("El titulo no puede ser nulo");
        }        
        if(cuerpo == null || cuerpo.isEmpty()){
            throw new MiExcepcion("El cuerpo no puede ser nulo");
        }        
    }
    
}
