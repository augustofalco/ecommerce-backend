// Ruta: src/main/java/com/ecommerce/api/service/VentaService.java
package com.ecommerce.api.service;

import com.ecommerce.api.dto.ItemVentaDTO;
import com.ecommerce.api.dto.VentaRequestDTO;
import com.ecommerce.api.model.*;
import com.ecommerce.api.repository.ClienteRepository;
import com.ecommerce.api.repository.ProductoRepository;
import com.ecommerce.api.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <-- EL IMPORT CORRECTO

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmailService emailService;

    private static final int UMBRAL_BAJO_STOCK = 10;

    @Transactional
    public Venta crearVenta(VentaRequestDTO ventaRequest, String clienteEmail) {
        Cliente cliente = clienteRepository.findByEmail(clienteEmail)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Venta venta = new Venta();
        venta.setNumeroDePedido(UUID.randomUUID().toString());
        venta.setFecha(LocalDateTime.now());
        venta.setCliente(cliente);
        venta.setMetodoPago(ventaRequest.getMetodoPago());

        List<DetalleVenta> detalles = new ArrayList<>();
        double totalVenta = 0;

        for (ItemVentaDTO itemDTO : ventaRequest.getItems()) {
            Producto producto = productoRepository.findById(itemDTO.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + itemDTO.getProductoId()));

            if (producto.getCantidad() < itemDTO.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }
            int nuevoStock = producto.getCantidad() - itemDTO.getCantidad();
            producto.setCantidad(nuevoStock);
            productoRepository.save(producto);

            if (nuevoStock <= UMBRAL_BAJO_STOCK) {
                emailService.notificarBajoStockAdmin(producto.getNombre(), nuevoStock);
            }

            DetalleVenta detalleVenta = new DetalleVenta();
            detalleVenta.setProducto(producto);
            detalleVenta.setCantidad(itemDTO.getCantidad());
            detalleVenta.setPrecioUnitario(producto.getPrecio());
            detalleVenta.setVenta(venta);
            detalles.add(detalleVenta);

            totalVenta += itemDTO.getCantidad() * producto.getPrecio();
        }

        venta.setDetalles(detalles);
        venta.setTotal(totalVenta);

        Venta ventaGuardada = ventaRepository.save(venta);

        emailService.notificarVentaAdmin(ventaGuardada);
        emailService.notificarCompraCliente(ventaGuardada);

        return ventaGuardada;
    }
}