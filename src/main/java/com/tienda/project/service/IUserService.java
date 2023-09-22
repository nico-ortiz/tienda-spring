package com.tienda.project.service;

import java.util.List;

import com.tienda.project.model.User;

public interface IUserService {
    
    public User createUser(User user);

    public List<User> getUsers();

    public User getUser(Long idUser);

    public User deleteUser(Long idUser);

    public User updateUser(Long idUser, User user);
}
