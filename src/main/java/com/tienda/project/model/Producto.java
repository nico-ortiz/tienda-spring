package com.tienda.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long codigoProducto;

    private String nombre;

    private String marca;

    private Double costo;

    private Double cantidadDisponible;

    public Producto() {}

	public Producto(Long codigoProducto, String nombre, String marca, Double costo, Double cantidadDisponible) {
		this.codigoProducto = codigoProducto;
		this.nombre = nombre;
		this.marca = marca;
		this.costo = costo;
		this.cantidadDisponible = cantidadDisponible;
	}

	public Producto(String nombre, String marca, Double costo, Double cantidadDisponible) {
		this.nombre = nombre;
		this.marca = marca;
		this.costo = costo;
		this.cantidadDisponible = cantidadDisponible;
	}
}
