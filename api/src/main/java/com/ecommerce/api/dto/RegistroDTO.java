package com.ecommerce.api.dto;

import lombok.Data;

@Data
public class RegistroDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String password;
}