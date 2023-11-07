package com.tienda.project.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Entity
@Table(name = "usuario")
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idUser;

    private String nombre;

    private String apellido;

	@Column(unique = true)
    private String username;

	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

    public User() {}

	public User(Long idUser, String nombre, String apellido, String username, String password) {
		this.idUser = idUser;
		this.nombre = nombre;
		this.apellido = apellido;
		this.username = username;
		this.password = password;
	}

	public User(Long idUser, String nombre, String apellido, String username, String password, Role role) {
		this.idUser = idUser;
		this.nombre = nombre;
		this.apellido = apellido;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public User(String nombre, String apellido, String username, String password, Role role) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public User(Long idUser, String nombre, String apellido, String username) {
		this.idUser = idUser;
		this.nombre = nombre;
		this.apellido = apellido;
		this.username = username;
	}

	public User(String nombre, String apellido, String username) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.username = username;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + this.role));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
