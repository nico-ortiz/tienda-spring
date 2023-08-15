package com.tienda.project.service;

import java.util.List;

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

	@Override
	public List<Producto> getProductos() {
		return productoRepository.findAll();
	}

	@Override
	public Producto getProducto(Long codigoProducto) {
		return productoRepository.findById(codigoProducto).orElse(null);
	}

	@Override
	public Producto deleteProducto(Long codigoProducto) {
		Producto productoD = this.getProducto(codigoProducto);
		productoRepository.deleteById(codigoProducto);
		return productoD;
	}

	@Override
	public Producto updateProducto(Long codigoProducto, Producto producto) {
		return this.createProducto(producto);
	}
    
}
