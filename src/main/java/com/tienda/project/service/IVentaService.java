package com.tienda.project.service;

import java.time.LocalDate;
import java.util.List;

import com.tienda.project.additionalFunctions.Pair;
import com.tienda.project.dto.VentaDTO;
import com.tienda.project.model.Producto;
import com.tienda.project.model.Venta;

public interface IVentaService {
    
    public Venta createVenta(Venta venta);

    public List<Venta> getVentas();

    public Venta getVenta(Long idVenta);

    public Venta deleteVenta(Long idVenta);
    
    public Venta updateVenta(Long idVenta, Venta venta);

    public Venta addProductoToVenta(Long codigoVenta, Long codigoProducto);

    public Venta deleteProductoToVenta(Long codigoVenta, Long codigoProducto);

    public List<Producto> getProductosByAVenta(Long codigoVenta);

    public Pair<Double> getTotalPriceAndTotalCountsOfVentasByADay(LocalDate fechaVenta);

    public VentaDTO getMoreExpensiveVenta();
}
