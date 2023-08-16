package com.tienda.project.service;

import java.util.List;

import com.tienda.project.model.Cliente;

public interface IClienteService {
    
    public Cliente createCliente(Cliente cliente);

    public List<Cliente> getClientes();

    public Cliente getCliente(Long idCliente);

    public Cliente deleteCliente(Long idCliente);

    public Cliente updateCliente(Long idCliente, Cliente cliente);
}
