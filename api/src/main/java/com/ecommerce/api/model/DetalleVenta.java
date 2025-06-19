package com.ecommerce.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class DetalleVenta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "venta_id")
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto; // 
    
    private int cantidad; // 
    
    private double precioUnitario;
}