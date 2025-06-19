package com.ecommerce.api.controller;

import com.ecommerce.api.dto.RegistroDTO;
import com.ecommerce.api.model.Cliente;
import com.ecommerce.api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registrarCliente(@RequestBody RegistroDTO registroDTO) {
        try {
            Cliente nuevoCliente = authService.registrar(registroDTO);
            return ResponseEntity.ok("Usuario registrado exitosamente con ID: " + nuevoCliente.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}