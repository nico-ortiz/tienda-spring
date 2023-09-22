package com.tienda.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.tienda.project.model.User;
import com.tienda.project.service.IUserService;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private IUserService userService;

    @PostMapping("/crear")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User userSaved = userService.createUser(user);
        return new ResponseEntity<User>(userSaved, HttpStatus.CREATED);
    }

    @GetMapping("/traer")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<User> getUser(@PathVariable Long idUser) {
        return ResponseEntity.ok(userService.getUser(idUser));
    }

    @DeleteMapping("/eliminar/{idUser}")
    public ResponseEntity<User> deleteUser(@PathVariable Long idUser) {
        User userDeleted = userService.deleteUser(idUser);
        return new ResponseEntity<User>(userDeleted, HttpStatus.ACCEPTED);
    }   

    @PutMapping("/editar/{idUser}")
    public ResponseEntity<User> updateUser(@PathVariable Long idUser, @RequestBody User user) {
        userService.updateUser(idUser, user);
        return ResponseEntity.ok(userService.getUser(user.getIdUser()));
    }
}
