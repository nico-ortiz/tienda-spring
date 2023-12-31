package com.tienda.project.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.project.additionalFunctions.Pair;
import com.tienda.project.dao.IProductoRepository;
import com.tienda.project.dao.IUserRepository;
import com.tienda.project.dao.IVentaRepository;
import com.tienda.project.dto.ProductoDTO;
import com.tienda.project.dto.VentaDTO;
import com.tienda.project.model.Producto;
import com.tienda.project.model.User;
import com.tienda.project.model.Venta;

@Service
public class VentaService implements IVentaService {

    @Autowired
    private IVentaRepository ventaRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IProductoRepository productoRepository;

    @Override
    public Venta createVenta(Venta venta) {
        Long idUser = venta.getUser().getIdUser();
        User user = userRepository.findById(idUser).orElse(null);
        
        if (user != null) {
            venta.setUser(user);
            return ventaRepository.save(venta);
        }
        return null;
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
    public Venta updateVenta(Long idVenta, Venta venta) {
        venta.setCodigoVenta(idVenta);
        return this.createVenta(venta);
    }

    @Override
    //Check excetions
    public Venta addProductoToVenta(Long codigoVenta, Long codigoProducto) {
        Venta venta = ventaRepository.findById(codigoVenta).get();
        Producto producto = productoRepository.findById(codigoProducto).get();
        
        //Control stock
        if (producto.getCantidadDisponible() > 0) {
            producto.setCantidadDisponible(producto.getCantidadDisponible() - 1);
            venta.addProducto(producto);
            venta.setTotal(venta.getTotal() + producto.getCosto());
            return this.updateVenta(venta.getCodigoVenta(), venta);
        } else {
            return null;
        }     
    }

    @Override
    //CheckExceptions
    public Venta deleteProductoToVenta(Long codigoVenta, Long codigoProducto) {
        Venta venta = ventaRepository.findById(codigoVenta).get();
        Producto producto = productoRepository.findById(codigoProducto).get();
        
        if (!venta.getListaProductos().contains(producto)) 
            return null;

        venta.removeProducto(producto);
        venta.setTotal(venta.getTotal() - producto.getCosto());
        producto.setCantidadDisponible(producto.getCantidadDisponible() + 1);
        return this.updateVenta(codigoVenta, venta);
    }

    @Override
    public List<Producto> getProductosByAVenta(Long codigoVenta) {
        Venta venta = this.getVenta(codigoVenta);
        return venta.getListaProductos();
    }

    @Override
    public Pair<Double> getTotalPriceAndTotalCountsOfVentasByADay(LocalDate fechaVenta) {
        List<Venta> ventas = this.getVentas();
        Double totalPrice = 0.0, totalCount = 0.0;

        for (Venta venta: ventas) {
            if (venta.getFechaVenta().compareTo(fechaVenta) == 0) {
                totalPrice += venta.getTotal();
                totalCount++;
            }
        }
        return new Pair<Double>(totalPrice, totalCount);
    }

    @Override
    public VentaDTO getMoreExpensiveVenta() {
        List<Venta> ventas = this.getVentas();
        Double maxPriceTotal = -Double.MIN_VALUE;
        Venta maxVenta = new Venta();

        for (Venta venta: ventas) {
            if (Double.compare(venta.getTotal(), maxPriceTotal) > 0) {
                maxPriceTotal = venta.getTotal();
                maxVenta = venta;
            }
        }

        if (Double.compare(maxPriceTotal,0.0d) == 0) {
            return new VentaDTO(maxVenta.getCodigoVenta(), maxPriceTotal, 0, null, null);
        } 
        return new VentaDTO(maxVenta.getCodigoVenta(), maxPriceTotal, maxVenta.getListaProductos().size(), maxVenta.getUser().getNombre(), maxVenta.getUser().getApellido());
    }

    @Override
    public void getListProductosDTOFromVenta(List<Producto> listProducto, List<ProductoDTO> list) {
        ProductoDTO productoDTO = new ProductoDTO();

        for (Producto producto: listProducto) {
            productoDTO.setCodigoProducto(producto.getCodigoProducto());
            productoDTO.setCosto(producto.getCosto());
            productoDTO.setMarca(producto.getMarca());
            productoDTO.setNombre(producto.getNombre());
            list.add(productoDTO);
        }
    }
}
