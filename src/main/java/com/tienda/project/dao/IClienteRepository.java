package com.tienda.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tienda.project.model.Cliente;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long>{
    
}
