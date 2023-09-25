package com.tienda.project.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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

    private Double total = 0.0;

    @ManyToMany
	@JsonIgnore
	@JoinTable(
		name = "ventas_productos",
		joinColumns = @JoinColumn(name = "venta_id", referencedColumnName = "codigoVenta" ),
		inverseJoinColumns = @JoinColumn(name = "producto_id", referencedColumnName = "codigoProducto")
	)
    private List<Producto> listaProductos = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cliente_id", referencedColumnName = "idCliente")
    private User user;

	public Venta() {}

	public Venta(Long codigoVenta, LocalDate fechaVenta, Double total, User user, List<Producto> listaProductos) {
		this.codigoVenta = codigoVenta;
		this.fechaVenta = fechaVenta;
		this.total = total;
		this.user = user;
		this.listaProductos = listaProductos;
	}

	public Venta(Long codigoVenta, LocalDate fechaVenta, Double total, User user) {
		this.codigoVenta = codigoVenta;
		this.fechaVenta = fechaVenta;
		this.total = total;
		this.user = user;
	}

	public Venta(LocalDate fechaVenta, Double total, User user) {
		this.fechaVenta = fechaVenta;
		this.total = total;
		this.user = user;
	}
	
	public Venta(LocalDate fechaVenta, User user) {
		this.fechaVenta = fechaVenta;
		this.user = user;
	}
	
	public void addProductos(List<Producto> list) {
		for (Producto producto: list) {
			this.listaProductos.add(producto);
		}
	}

	public void addProducto(Producto producto) {
		this.listaProductos.add(producto);
		if (!producto.getVentas().contains(this)) {
			producto.getVentas().add(this);
		}
	}

	public void remove(Producto producto) {
		this.listaProductos.remove(producto);
		if (producto.getVentas().contains(this)) {
			producto.getVentas().remove(this);
		}

	}
}
