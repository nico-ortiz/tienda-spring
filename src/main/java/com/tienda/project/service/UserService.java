package com.tienda.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tienda.project.dao.IUserRepository;
import com.tienda.project.model.User;

@Service
@PreAuthorize("hasRole('ADMIN')")
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired 
    private  PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long idUser) {
        return userRepository.findById(idUser).orElse(null);
    }

    @Override
    public User deleteUser(Long idUser) {
        User user = this.getUser(idUser);
        userRepository.deleteById(idUser);
        return user;
    }

    @Override
    public User updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
