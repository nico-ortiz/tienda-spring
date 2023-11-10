package com.tienda.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.tienda.project.dao.IProductoRepository;
import com.tienda.project.model.Producto;
import com.tienda.project.model.Venta;

@Service
public class ProductoService implements IProductoService{

    @Autowired
    private IProductoRepository productoRepository;

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public Producto createProducto(Producto producto) {
		return productoRepository.save(producto);
	}

	@Override
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public List<Producto> getProductos() {
		return productoRepository.findAll();
	}

	@Override
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public Producto getProducto(Long codigoProducto) {
		return productoRepository.findById(codigoProducto).orElse(null);
	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public Producto deleteProducto(Long codigoProducto) {
		Producto productoD = this.getProducto(codigoProducto);
		productoRepository.deleteById(codigoProducto);
		return productoD;
	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public Producto updateProducto(Producto producto) {
		return this.createProducto(producto);
	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public List<Producto> getProductosWhoseStockLessThanFive() {
		List<Producto> products = this.getProductos();		
		List<Producto> list = new ArrayList<>();

		for (Producto product: products) {
			if (product.getCantidadDisponible() < 5) {
				list.add(product);
			}
		}
		return list;
	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public List<Venta> getVentasByCodigoProducto(Long codigoProducto) {
		Producto producto = this.getProducto(codigoProducto);
		if (producto != null) {
			return producto.getVentas();
		}
		return null;
	}
    
}
