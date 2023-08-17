package com.tienda.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.project.dao.IClienteRepository;
import com.tienda.project.dao.IVentaRepository;
import com.tienda.project.model.Cliente;
import com.tienda.project.model.Venta;

@Service
public class VentaService implements IVentaService{

    @Autowired
    private IVentaRepository ventaRepository;

    @Autowired
    private IClienteRepository clienteRepository;

    @Override
    public Venta createVenta(Venta venta) {
        Long idCliente = venta.getCliente().getIdCliente();
        Cliente cliente = clienteRepository.findById(idCliente).get();
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
        ventaRepository.delete(venta);
        return venta;
    }

    @Override
    public Venta updateVenta(Venta venta) {
        return this.createVenta(venta);
    }
}
