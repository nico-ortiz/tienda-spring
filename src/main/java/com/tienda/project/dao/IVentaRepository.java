package com.tienda.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tienda.project.model.Venta;

public interface IVentaRepository extends JpaRepository<Venta, Long>{
    
}
