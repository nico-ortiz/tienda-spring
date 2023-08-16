package com.tienda.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.project.model.Cliente;
import com.tienda.project.service.IClienteService;

@RestController
public class ClienteController {
    
    @Autowired
    private IClienteService clienteService;

    @PostMapping("/clientes/crear")
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.createCliente(cliente));
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> getCliente() {
        return ResponseEntity.ok(clienteService.getClientes());
    }

    
}
