package com.ecommerce.api.controller;

import com.ecommerce.api.model.Producto;
import com.ecommerce.api.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // Endpoint público para que los clientes vean los productos 
    @GetMapping
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }
    
    // El resto de endpoints son para el Administrador 

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Producto crearProducto(@RequestBody Producto producto) {
       return productoRepository.save(producto);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto detallesProducto) {
        return productoRepository.findById(id)
                .map(producto -> {
                    producto.setNombre(detallesProducto.getNombre());
                    producto.setMarca(detallesProducto.getMarca());
                    producto.setCategoria(detallesProducto.getCategoria());
                    producto.setColor(detallesProducto.getColor());
                    producto.setCantidad(detallesProducto.getCantidad());
                    producto.setPrecio(detallesProducto.getPrecio());
                    return ResponseEntity.ok(productoRepository.save(producto));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        return productoRepository.findById(id)
                .map(producto -> {
                    productoRepository.delete(producto);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
    
    // Endpoint para que el admin vea el stock de cada producto 
    @GetMapping("/stock")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Producto> verStock() {
        return productoRepository.findAll();
    }

    @GetMapping("/test")
    public String testEndpoint() {
        return "El endpoint de prueba funciona!";
    }
}
