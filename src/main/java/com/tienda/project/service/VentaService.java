package com.tienda.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.project.dao.IClienteRepository;
import com.tienda.project.dao.IProductoRepository;
import com.tienda.project.dao.IVentaRepository;
import com.tienda.project.model.Cliente;
import com.tienda.project.model.Producto;
import com.tienda.project.model.Venta;

@Service
public class VentaService implements IVentaService{

    @Autowired
    private IVentaRepository ventaRepository;

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private IProductoRepository productoRepository;

    @Override
    public Venta createVenta(Venta venta) {
        Long idCliente = venta.getCliente().getIdCliente();
        Cliente cliente = clienteRepository.findById(idCliente).orElse(null);
        venta.setCliente(cliente);
        return ventaRepository.save(venta);
    }

    @Override
    public List<Venta> getVentas() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta getVenta(Long idVenta) {
        return ventaRepository.findById(idVenta).orElse(null);
    }

    @Override
    public Venta deleteVenta(Long idVenta) {
        Venta venta = this.getVenta(idVenta);
        ventaRepository.deleteById(idVenta);
        return venta;
    }

    @Override
    public Venta updateVenta(Venta venta) {
        return this.createVenta(venta);
    }

    @Override
    public Venta addProductoToVenta(Long codigoVenta, Long codigoProducto) {
        Venta venta = ventaRepository.findById(codigoVenta).get();
        Producto producto = productoRepository.findById(codigoProducto).get();
        
        //Control stock
        if (producto.getCantidadDisponible() > 0) {
            producto.setCantidadDisponible(producto.getCantidadDisponible() - 1);
            venta.addProducto(producto);
            venta.setTotal(venta.getTotal() + producto.getCosto());
            return this.updateVenta(venta);
        } else {
            return null;
        }
        
    }

    @Override
    public Venta deleteProductoToVenta(Long codigoVenta, Long codigoProducto) {
        Venta venta = ventaRepository.findById(codigoVenta).get();
        Producto producto = productoRepository.findById(codigoProducto).get();
        venta.remove(producto);
        return this.updateVenta(venta);
    }

    @Override
    public List<Producto> getProductosByAVenta(Long codigoVenta) {
        Venta venta = this.getVenta(codigoVenta);
        return venta.getListaProductos();
    }
}
