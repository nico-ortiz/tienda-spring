package com.tienda.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idUser;

    private String nombre;

    private String apellido;

	@Column(nullable = false)
    private String dni;

	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

    public User() {}

	public User(Long idUser, String nombre, String apellido, String dni, String password) {
		this.idUser = idUser;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.password = password;
	}

	public User(String nombre, String apellido, String dni, String password, Role role) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.password = password;
		this.role = role;
	}

	public User(Long idUser, String nombre, String apellido, String dni) {
		this.idUser = idUser;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
	}

	public User(String nombre, String apellido, String dni) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
	}
}
