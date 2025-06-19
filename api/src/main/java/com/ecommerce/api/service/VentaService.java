// Asegúrate de que esta línea sea exactamente así
package com.ecommerce.api.service;

import com.ecommerce.api.model.Producto;
import com.ecommerce.api.model.Venta;
import com.ecommerce.api.repository.ProductoRepository;
import com.ecommerce.api.repository.VentaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; // <-- ¡ESTA ANOTACIÓN ES CRUCIAL!

// Y que la clase se llame VentaService y tenga la anotación @Service
@Service
public class VentaService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private VentaRepository ventaRepository;
    
    @Autowired
    private EmailService emailService;

    private static final int UMBRAL_BAJO_STOCK = 10;

    @Transactional
    public Venta crearVenta(Venta venta) {
        // 1. Actualizar el stock de cada producto en tiempo real
        venta.getDetalles().forEach(detalle -> {
            Producto producto = productoRepository.findById(detalle.getProducto().getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            
            if (producto.getCantidad() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }
            
            int nuevoStock = producto.getCantidad() - detalle.getCantidad();
            producto.setCantidad(nuevoStock);
            productoRepository.save(producto);
            
            // 2. Verificar si el stock es bajo después de la venta
            if (nuevoStock <= UMBRAL_BAJO_STOCK) {
                emailService.notificarBajoStockAdmin(producto.getNombre(), nuevoStock);
            }
        });
        
        // 3. Guardar la venta
        Venta ventaGuardada = ventaRepository.save(venta);
        
        // 4. Enviar notificaciones por email automatizadas
        emailService.notificarCompraCliente(ventaGuardada);
        emailService.notificarVentaAdmin(ventaGuardada);
        
        return ventaGuardada;
    }
}