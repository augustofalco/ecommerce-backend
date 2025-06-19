package com.ecommerce.api.controller;

import com.ecommerce.api.model.Venta;
import com.ecommerce.api.repository.VentaRepository;
import com.ecommerce.api.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;
    
    @Autowired
    private VentaRepository ventaRepository;

    // Endpoint para que un cliente realice una compra 
    @PostMapping
    @PreAuthorize("isAuthenticated()") // Solo usuarios logueados pueden comprar
    public Venta realizarVenta(@RequestBody Venta venta) {
        // La integración con Mercado Pago  se haría aquí antes de llamar a crearVenta,
        // o se recibiría una notificación de pago de Mercado Pago para confirmar.
        return ventaService.crearVenta(venta);
    }
    
    // Endpoint para que el administrador vea las ventas 
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Venta> obtenerVentas() {
        return ventaRepository.findAll();
    }
}