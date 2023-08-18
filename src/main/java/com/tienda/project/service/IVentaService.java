package com.tienda.project.service;

import java.util.List;

import com.tienda.project.model.Venta;

public interface IVentaService {
    
    public Venta createVenta(Venta venta);

    public List<Venta> getVentas();

    public Venta getVenta(Long idVenta);

    public Venta deleteVenta(Long idVenta);
    
    public Venta updateVenta(Venta venta);

    public Venta addProductoToVenta(Long codigoVenta, Long codigoProducto);
}
