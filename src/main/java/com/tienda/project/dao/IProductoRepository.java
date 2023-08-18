package com.tienda.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tienda.project.model.Producto;

public interface IProductoRepository extends JpaRepository<Producto, Long>{
    
}
