package com.ecommerce.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;

    @Column(unique = true)
    private String email;

    private String password;

    private String dni;
    private String celular;

    // Datos de envío
    private String domicilio;
    private String localidad;
    private String codigoPostal;
    private String provincia;

    @Enumerated(EnumType.STRING)
    private Role role;
}
