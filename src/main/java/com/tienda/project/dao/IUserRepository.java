package com.tienda.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tienda.project.model.User;

public interface IUserRepository extends JpaRepository<User, Long>{
    
}
