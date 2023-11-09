package com.tienda.project.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tienda.project.additionalFunctions.Pair;
import com.tienda.project.dto.ProductoDTO;
import com.tienda.project.dto.UserDTO;
import com.tienda.project.dto.VentaDTO;
import com.tienda.project.model.Producto;
import com.tienda.project.model.Venta;
import com.tienda.project.service.IProductoService;
import com.tienda.project.service.IUserService;
import com.tienda.project.service.IVentaService;


@RestController
@RequestMapping("/ventas")
public class VentaController {
    
    @Autowired
    private IVentaService ventaService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IProductoService productoService;

    @PostMapping("/crear")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<VentaDTO> createVenta(@RequestBody Venta venta) {
        Venta ventaSaved = ventaService.createVenta(venta);

        if (ventaSaved !=null) {
            VentaDTO ventaDTO = new VentaDTO(
                ventaSaved.getCodigoVenta(),
                ventaSaved.getTotal(),
                ventaSaved.getListaProductos().size(),
                ventaSaved.getUser().getNombre(),
                ventaSaved.getUser().getApellido()
            );
            return new ResponseEntity<VentaDTO>(ventaDTO, HttpStatus.CREATED);
        }
        //User not found
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @GetMapping("/traer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Venta>> getVentas() {
        return ResponseEntity.ok(ventaService.getVentas());
    }
    
    @PatchMapping("/{codigoVenta}/listaProductos/add/{codigoProducto}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<ProductoDTO>> addProductoToVenta(
        @PathVariable Long codigoVenta,
        @PathVariable Long codigoProducto
    ) {
        if (ventaExists(codigoVenta)) {
            if (productoExists(codigoProducto)) {    
                Venta venta = ventaService.addProductoToVenta(codigoVenta, codigoProducto);
                
                //Stock control
                if (venta != null)  {
                    List<ProductoDTO> listProductoDTO = new ArrayList<>();
                    ventaService.getListProductosDTOFromVenta(venta.getListaProductos(), listProductoDTO);
                    return ResponseEntity.ok(listProductoDTO);
                }
                else
                    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Product out of stock");
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found");
    }

    @PatchMapping("{codigoVenta}/listaProductos/del/{codigoProducto}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<ProductoDTO>> deleteProductoToVenta(
        @PathVariable Long codigoVenta,
        @PathVariable Long codigoProducto
    ) {
        if (ventaExists(codigoVenta)) {
            if (productoExists(codigoProducto)) { 
                Venta venta = ventaService.deleteProductoToVenta(codigoVenta, codigoProducto);      
                if(venta != null) {
                    List<ProductoDTO> listProductoDTO = new ArrayList<>();
                    ventaService.getListProductosDTOFromVenta(venta.getListaProductos(), listProductoDTO);
                    return new ResponseEntity<List<ProductoDTO>>(listProductoDTO, HttpStatus.OK);
                }
                else
                    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Product out of stock");
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found");
    }

    @GetMapping("{codigoVenta}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Venta> getVenta(@PathVariable Long codigoVenta) {
        if (ventaExists(codigoVenta)) {
            return ResponseEntity.ok(ventaService.getVenta(codigoVenta));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found");
    }

    @DeleteMapping("/eliminar/{codigoVenta}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VentaDTO> deleteVenta(@PathVariable Long codigoVenta) {
        Venta ventaDeleted = ventaService.deleteVenta(codigoVenta);
        if (ventaDeleted != null) {
            VentaDTO ventaDTO = new VentaDTO(
                ventaDeleted.getCodigoVenta(),
                ventaDeleted.getTotal(),
                ventaDeleted.getListaProductos().size(),
                ventaDeleted.getUser().getNombre(),
                ventaDeleted.getUser().getApellido()
            );
            return new ResponseEntity<VentaDTO>(ventaDTO, HttpStatus.ACCEPTED);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found");
    }

    @PutMapping("/editar/{codigoVenta}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Venta> updateVenta(@PathVariable Long codigoVenta, @RequestBody Venta venta) {
        if (ventaExists(codigoVenta)) { 
            if (userService.getUser(venta.getUser().getIdUser()) != null) {
                ventaService.updateVenta(codigoVenta, venta);
                return ResponseEntity.ok(ventaService.getVenta(venta.getCodigoVenta()));
            } 
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found");
    }

    @GetMapping("/{codigoVenta}/productos")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<ProductoDTO>> getProductosByAVenta(@PathVariable Long codigoVenta) {
        if (!ventaExists(codigoVenta)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found");
        }
        List<Producto> listProducto = ventaService.getProductosByAVenta(codigoVenta);
        List<ProductoDTO> list = new ArrayList<>();
        ventaService.getListProductosDTOFromVenta(listProducto, list);
        return ResponseEntity.ok(list);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Double>> getTotalVentasByADay(
        @RequestParam(name = "fechaVenta") LocalDate fechaVenta) {
        Pair<Double> pair = ventaService.getTotalPriceAndTotalCountsOfVentasByADay(fechaVenta);
        Double totalPrice = pair.getFst();
        Double totalCount = pair.getSnd();
        Map<String, Double> json = new HashMap<>();
        
        json.put("totalPrice", totalPrice);
        json.put("totalCount", totalCount);

        return ResponseEntity.ok(json);
    }

    @GetMapping("/mayorVenta")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VentaDTO> getMoreExpensiveVenta() {
        return ResponseEntity.ok(ventaService.getMoreExpensiveVenta());
    }

    private boolean ventaExists(Long codigoVenta) {
        return ventaService.getVenta(codigoVenta) != null;
    }

    private boolean productoExists(Long codigoProducto) {
        return productoService.getProducto(codigoProducto) != null;
    }
}
