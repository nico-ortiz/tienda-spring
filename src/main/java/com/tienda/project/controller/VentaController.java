package com.tienda.project.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.tienda.project.additionalFunctions.Pair;
import com.tienda.project.model.Producto;
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
        Venta venta = ventaService.addProductoToVenta(codigoVenta, codigoProducto);
        
        if (venta != null) 
            return ResponseEntity.ok(venta);
        else
            return new ResponseEntity<Venta>(HttpStatus.METHOD_NOT_ALLOWED);
    }
    
    @GetMapping("{codigoVenta}")
    public ResponseEntity<Venta> getVenta(@PathVariable Long codigoVenta) {
        return ResponseEntity.ok(ventaService.getVenta(codigoVenta));
    }

    @DeleteMapping("/eliminar/{codigoVenta}")
    public ResponseEntity<Venta> deleteVenta(@PathVariable Long codigoVenta) {
        return ResponseEntity.ok(ventaService.deleteVenta(codigoVenta));
    }

    @PutMapping("/editar/{codigoVenta}")
    public ResponseEntity<Venta> updateVenta(@PathVariable Long codigoVenta, @RequestBody Venta venta) {
        ventaService.updateVenta(venta);
        return ResponseEntity.ok(ventaService.getVenta(venta.getCodigoVenta()));
    }

    @GetMapping("/productos/{codigoVenta}")
    public ResponseEntity<List<Producto>> getProductosByAVenta(@PathVariable Long codigoVenta) {
        return ResponseEntity.ok(ventaService.getProductosByAVenta(codigoVenta));
    }

    @GetMapping("")
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
}
