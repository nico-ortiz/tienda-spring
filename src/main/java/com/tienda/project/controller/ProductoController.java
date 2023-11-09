package com.tienda.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.project.model.Producto;
import com.tienda.project.model.Venta;
import com.tienda.project.service.IProductoService;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    
    @Autowired
    private IProductoService productoService;

    @PostMapping("/crear")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
        Producto productoSaved = productoService.createProducto(producto);
        return new ResponseEntity<Producto>(productoSaved, HttpStatus.CREATED);
    }

    @GetMapping("/traer")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<Producto>> getProductos() {
        return ResponseEntity.ok(productoService.getProductos());
    }

    @GetMapping("/{codigoProducto}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Producto> getProducto(@PathVariable Long codigoProducto) {
        Producto producto = productoService.getProducto(codigoProducto);
        if (producto != null) {
            return ResponseEntity.ok(productoService.getProducto(codigoProducto));
        }
        return new ResponseEntity<Producto>(producto, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/eliminar/{codigoProducto}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Producto> deleteProduct(@PathVariable Long codigoProducto) {
        Producto productoDeleted = productoService.deleteProducto(codigoProducto);
        if (productoDeleted == null) {
            return  new ResponseEntity<Producto>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Producto>(productoDeleted, HttpStatus.ACCEPTED);
    }

    @PutMapping("/editar/{codigoProducto}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long codigoProducto, @RequestBody Producto producto) {        
        Producto productoToUpdate = productoService.getProducto(codigoProducto);
        if (productoToUpdate != null ) {
            producto.setCodigoProducto(codigoProducto);
            productoService.updateProducto(producto);
            return ResponseEntity.ok(productoService.getProducto(codigoProducto));
        }
        return new ResponseEntity<Producto>(productoToUpdate, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/falta-stock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Producto>> getProductosStock() {
        return ResponseEntity.ok(productoService.getProductosWhoseStockLessThanFive());
    }

    @GetMapping("{codigoProducto}/listaVentas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Venta>> getListaVentasDeProductoById(@PathVariable Long codigoProducto) {
        Producto producto = productoService.getProducto(codigoProducto);
        if (producto != null) {
            return ResponseEntity.ok(productoService.getVentasByCodigoProducto(codigoProducto));
        }
        return new ResponseEntity<List<Venta>>(List.of(), HttpStatus.NOT_FOUND);
    }
}
