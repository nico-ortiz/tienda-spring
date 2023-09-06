package com.tienda.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.tienda.project.model.Cliente;
import com.tienda.project.service.IClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    
    @Autowired
    private IClienteService clienteService;

    @PostMapping("/crear")
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
        Cliente clienteSaved = clienteService.createCliente(cliente);
        return new ResponseEntity<Cliente>(clienteSaved, HttpStatus.CREATED);
    }

    @GetMapping("/traer")
    public ResponseEntity<List<Cliente>> getClientes() {
        return ResponseEntity.ok(clienteService.getClientes());
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<Cliente> getCliente(@PathVariable Long idCliente) {
        return ResponseEntity.ok(clienteService.getCliente(idCliente));
    }

    @DeleteMapping("/eliminar/{idCliente}")
    public ResponseEntity<Cliente> deleteCliente(@PathVariable Long idCliente) {
        Cliente clienteDeleted = clienteService.deleteCliente(idCliente);
        return new ResponseEntity<Cliente>(clienteDeleted, HttpStatus.ACCEPTED);
    }   

    @PutMapping("/editar/{idCliente}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long idCliente, @RequestBody Cliente cliente) {
        clienteService.updateCliente(idCliente, cliente);
        return ResponseEntity.ok(clienteService.getCliente(cliente.getIdCliente()));
    }
}
