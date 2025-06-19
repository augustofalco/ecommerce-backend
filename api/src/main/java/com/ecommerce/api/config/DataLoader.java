package com.ecommerce.api.config;

import com.ecommerce.api.model.Cliente;
import com.ecommerce.api.model.Role;
import com.ecommerce.api.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya existe un usuario admin para no crearlo de nuevo
        if (clienteRepository.findByEmail("admin@ecommerce.com").isEmpty()) {
            Cliente admin = new Cliente();
            admin.setNombre("Administrador");
            admin.setApellido("del Sistema");
            admin.setEmail("admin@ecommerce.com");
            admin.setPassword(passwordEncoder.encode("admin123")); 
            admin.setRole(Role.ROLE_ADMIN);
            
            clienteRepository.save(admin);
            System.out.println(">>> Usuario Administrador de prueba creado y contraseña codificada <<<");
        }
    }
}
