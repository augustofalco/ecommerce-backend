package com.ecommerce.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre; // 
    private String apellido; // 
    private String email; // Usado como identificador principal para el login con Google 
    private String dni; // 
    private String celular; // 
    
    // Datos de envío
    private String domicilio; // 
    private String localidad; // 
    private String codigoPostal; // 
    private String provincia; // 
}
