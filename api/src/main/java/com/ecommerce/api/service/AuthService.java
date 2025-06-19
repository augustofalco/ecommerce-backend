
package com.ecommerce.api.service;

import com.ecommerce.api.dto.RegistroDTO;
import com.ecommerce.api.model.Cliente;
import com.ecommerce.api.model.Role;
import com.ecommerce.api.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Cliente registrar(RegistroDTO registroDTO) {
        if (clienteRepository.findByEmail(registroDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Error: El email ya está en uso.");
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(registroDTO.getNombre());
        cliente.setApellido(registroDTO.getApellido());
        cliente.setEmail(registroDTO.getEmail());
        cliente.setPassword(passwordEncoder.encode(registroDTO.getPassword()));
        cliente.setRole(Role.ROLE_USER);

        return clienteRepository.save(cliente);
    }
}
