package com.tienda.project.service;

import java.util.List;

import com.tienda.project.model.Producto;

public interface IProductoService {
    
    public Producto createProducto(Producto producto);

    public List<Producto> getProductos();

    public Producto getProducto(Long codigoProducto);

    public Producto deleteProducto(Long codigoProducto);

    public Producto updateProducto(Producto producto);
}
