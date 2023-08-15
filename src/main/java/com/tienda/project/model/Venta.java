package com.tienda.project.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Venta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long codigoVenta;

    @JsonFormat(pattern = "dd/MM/yyyy", shape = Shape.STRING)
    private LocalDate fechaVenta;

    private Double total;

    @OneToMany
    private List<Producto> listaProductos;

    @OneToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "idCliente")
    private Cliente cliente;

	public Venta() {}

	public Venta(Long codigoVenta, LocalDate fechaVenta, Double total, List<Producto> listaProductos, Cliente cliente) {
		this.codigoVenta = codigoVenta;
		this.fechaVenta = fechaVenta;
		this.total = total;
		this.listaProductos = listaProductos;
		this.cliente = cliente;
	}

	public Venta(LocalDate fechaVenta, Double total, List<Producto> listaProductos, Cliente cliente) {
		this.fechaVenta = fechaVenta;
		this.total = total;
		this.listaProductos = listaProductos;
		this.cliente = cliente;
	}    
}
