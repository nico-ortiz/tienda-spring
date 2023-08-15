package com.tienda.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tienda.project.model.Producto;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Long>{
    
}
