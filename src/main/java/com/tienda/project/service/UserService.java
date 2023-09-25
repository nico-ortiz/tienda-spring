package com.tienda.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.project.dao.IUserRepository;
import com.tienda.project.model.User;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public User createUser(User user) {
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
        userRepository.deleteById(idUser);
        return this.getUser(idUser);
    }

    @Override
    public User updateUser(Long idUser, User user) {
        return this.createUser(user);
    }
    
}
