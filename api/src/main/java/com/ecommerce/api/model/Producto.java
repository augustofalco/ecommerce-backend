package com.ecommerce.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data // Lombok para generar getters, setters, toString, etc.
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre; // 

    private String marca; // 

    private String categoria; // 

    private String color; // 

    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private int cantidad; // 
    
    // Nota: El documento no menciona el precio, pero es esencial para un e-commerce.
    @Min(value = 0, message = "El precio no puede ser negativo")
    private double precio;
}