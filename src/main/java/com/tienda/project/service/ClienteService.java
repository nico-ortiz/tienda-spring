package com.tienda.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.project.dao.IClienteRepository;
import com.tienda.project.model.Cliente;

@Service
public class ClienteService implements IClienteService{

    @Autowired
    private IClienteRepository clienteRepository;

    @Override
    public Cliente createCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> getClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente getCliente(Long idCliente) {
        return clienteRepository.findById(idCliente).orElse(null);
    }

    @Override
    public Cliente deleteCliente(Long idCliente) {
        clienteRepository.deleteById(idCliente);
        return this.getCliente(idCliente);
    }

    @Override
    public Cliente updateCliente(Long idCliente, Cliente cliente) {
        return this.createCliente(cliente);
    }
    
}
