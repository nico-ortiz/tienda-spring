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
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User implements UserDetails {
    
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getUsername'");
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'isAccountNonExpired'");
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'isAccountNonLocked'");
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'isCredentialsNonExpired'");
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'isEnabled'");
	}  


}
