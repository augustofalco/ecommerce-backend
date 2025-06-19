package com.ecommerce.api.dto;

import java.util.List;
import lombok.Data;

@Data
public class VentaRequestDTO {
    private String metodoPago;
    private List<ItemVentaDTO> items;
}