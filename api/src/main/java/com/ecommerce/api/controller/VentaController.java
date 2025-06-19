// Ruta: src/main/java/com/ecommerce/api/controller/VentaController.java
package com.ecommerce.api.controller;

import com.ecommerce.api.dto.VentaRequestDTO;
import com.ecommerce.api.model.Venta;
import com.ecommerce.api.repository.VentaRepository;
import com.ecommerce.api.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "http://localhost:4200") // Habilitamos CORS
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private VentaRepository ventaRepository;

    @PostMapping("/comprar")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> realizarVenta(@RequestBody VentaRequestDTO ventaRequest, Authentication authentication) {
        try {
            String clienteEmail = authentication.getName();
            Venta nuevaVenta = ventaService.crearVenta(ventaRequest, clienteEmail);
            return ResponseEntity.ok(nuevaVenta);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Venta> obtenerVentas() {
        return ventaRepository.findAll();
    }
}