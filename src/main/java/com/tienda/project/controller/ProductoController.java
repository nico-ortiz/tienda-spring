package com.tienda.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.project.model.Producto;
import com.tienda.project.service.IProductoService;

@RestController
public class ProductoController {
    
    @Autowired
    private IProductoService productoService;

    @PostMapping("/productos/crear")
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.createProducto(producto));
    }

    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> getProductos() {
        return ResponseEntity.ok(productoService.getProductos());
    }

    @GetMapping("/productos/{codigoProducto}")
    public ResponseEntity<Producto> getProducto(@PathVariable Long codigoProducto) {
        return ResponseEntity.ok(productoService.getProducto(codigoProducto));
    }

    @DeleteMapping("/productos/eliminar/{codigoProducto}")
    public ResponseEntity<Producto> deleteProduct(@PathVariable Long codigoProducto) {
        return ResponseEntity.ok(productoService.deleteProducto(codigoProducto));
    }
}
