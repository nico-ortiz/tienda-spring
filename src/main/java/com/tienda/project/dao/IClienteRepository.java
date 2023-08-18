package com.tienda.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tienda.project.model.Cliente;

public interface IClienteRepository extends JpaRepository<Cliente, Long>{
    
}
