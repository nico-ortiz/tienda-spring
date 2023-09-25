package com.tienda.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VentaDTO {
    
    private Long codigoVenta;
    private Double total;
    private int cantidadProductos;
    private String nombreCliente;
    private String apellidoCliente;

    public VentaDTO() {}

    public VentaDTO(Long codigoVenta, Double total, int cantidadProductos, String nombreCliente,
            String apellidoCliente) {
        this.codigoVenta = codigoVenta;
        this.total = total;
        this.cantidadProductos = cantidadProductos;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
    }
}
