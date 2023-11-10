package com.tienda.project.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tienda.project.config.JwtService;
import com.tienda.project.dao.IUserRepository;
import com.tienda.project.model.Role;
import com.tienda.project.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        return createUser(request, false);
    }

    public AuthResponse registerAdmin(RegisterRequest request) {
        return createUser(request, true);
    }  

    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );  
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
            .token(token)
            .build();
    }

    private AuthResponse createUser(RegisterRequest request, boolean isAdmin) {
        User user = User.builder()
            .nombre(request.getNombre())
            .apellido(request.getApellido())
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(isAdmin ? Role.ROLE_ADMIN : Role.ROLE_USER)   
            .build();

        userRepository.save(user);
        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
            .token(token)
            .build();
    }
}