package com.tienda.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.project.dao.IProductoRepository;
import com.tienda.project.model.Producto;

@Service
public class ProductoService implements IProductoService{

    @Autowired
    private IProductoRepository productoRepository;

	@Override
	public Producto createProducto(Producto producto) {
		return productoRepository.save(producto);
	}
    
}
