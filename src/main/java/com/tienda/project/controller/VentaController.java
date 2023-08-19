package com.tienda.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.project.model.Venta;
import com.tienda.project.service.IVentaService;

@RestController
@RequestMapping("/ventas")
public class VentaController {
    
    @Autowired
    private IVentaService ventaService;

    @PostMapping("/crear")
    public ResponseEntity<Venta> createVenta(@RequestBody Venta venta) {
        return ResponseEntity.ok(ventaService.createVenta(venta));
    }

    @GetMapping("/traer")
    public ResponseEntity<List<Venta>> getVentas() {
        return ResponseEntity.ok(ventaService.getVentas());
    }
    
    @PutMapping("/{codigoVenta}/listaProductos/{codigoProducto}")
    public ResponseEntity<Venta> addProductoToVenta(
        @PathVariable Long codigoVenta,
        @PathVariable Long codigoProducto
    ) {
        return ResponseEntity.ok(ventaService.addProductoToVenta(codigoVenta, codigoProducto));
    }
    
    @GetMapping("{codigoVenta}")
    public ResponseEntity<Venta> getVenta(@PathVariable Long codigoVenta) {
        return ResponseEntity.ok(ventaService.getVenta(codigoVenta));
    }

    @DeleteMapping("/eliminar/{codigoVenta}")
    public ResponseEntity<Venta> deleteVenta(@PathVariable Long codigoVenta) {
        return ResponseEntity.ok(ventaService.deleteVenta(codigoVenta));
    }

    
}
