/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipo52.biblioteca.entidades;

import com.equipo52.biblioteca.enumeraciones.Rol;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Max
 */
@Entity
@Data
public class Usuario {
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2")
    private String id;
    
    private String nombre;
    private String password;
    private String email;
    
    @Enumerated(EnumType.STRING)
    private Rol rol;
    
    @OneToOne
    private Imagen imagen;
}
