package com.tienda.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
}
