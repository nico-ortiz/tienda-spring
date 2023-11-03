package com.tienda.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationProvider authenticationProvider;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String[] endpointsURL = {
            "/productos/traer",
            "/productos/{codigoProducto}",
            "/users/editar/{idUser}",
            "/ventas/crear",
            "/ventas/{codigoVenta}/listaProductos/add/{codigoProducto}",
            "/ventas/{codigoVenta}/listaProductos/del/{codigoProducto}",
            "/ventas/{codigoVenta}",
            "/ventas/productos/",
            //cambiar url ,
            "/productos/{codigoVenta}"
        };

        return http
            .csrf(csrf -> 
                csrf
                .disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers(endpointsURL).permitAll()
                .anyRequest().hasRole("ADMIN"))
            .sessionManagement(sessionManager->
                sessionManager 
                  .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}
