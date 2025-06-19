package com.ecommerce.api.service;

import com.ecommerce.api.model.Venta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Notificación para el cliente sobre su compra 
    public void notificarCompraCliente(Venta venta) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(venta.getCliente().getEmail());
        message.setSubject("Confirmación de tu pedido N° " + venta.getNumeroDePedido());
        // Aquí se construye el cuerpo del correo con los datos del pedido 
        message.setText("Hola " + venta.getCliente().getNombre() + ",\n\nGracias por tu compra..." +
                        "\nNúmero de Pedido: " + venta.getNumeroDePedido() +
                        "\nTotal: $" + venta.getTotal());
        mailSender.send(message);
    }

    // Notificación para el administrador sobre una nueva venta
public void notificarVentaAdmin(Venta venta) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo("admin@tu-ecommerce.com"); // Email del administrador
    message.setSubject("Nueva Venta Realizada - Pedido N° " + venta.getNumeroDePedido());
    // Aquí se construye el cuerpo del correo con los datos del cliente
    message.setText("Se ha realizado una nueva venta.\n\nDatos del Cliente:\n" +
                    "Nombre: " + venta.getCliente().getNombre() + " " + venta.getCliente().getApellido() + "\n" +
                    // LÍNEA CORREGIDA: getDNI() cambiado a getDni()
                    "DNI: " + venta.getCliente().getDni() + "\n" +
                    "Celular: " + venta.getCliente().getCelular());
    mailSender.send(message);
}
    
    // Notificación para el administrador sobre bajo stock 
    public void notificarBajoStockAdmin(String nombreProducto, int stockActual) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("admin@tu-ecommerce.com");
        message.setSubject("Alerta de Bajo Stock: " + nombreProducto);
        message.setText("El producto " + nombreProducto + " ha alcanzado un nivel bajo de stock." +
                        "\nStock actual: " + stockActual);
        mailSender.send(message);
    }
}