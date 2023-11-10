package com.tienda.project.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import org.springframework.web.server.ResponseStatusException;

import com.tienda.project.dto.UserDTO;
import com.tienda.project.model.User;
import com.tienda.project.service.IUserService;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private IUserService userService;

    @PostMapping("/crear")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User userSaved = userService.createUser(user);
            return new ResponseEntity<User>(userSaved, HttpStatus.CREATED);
        } catch(DataIntegrityViolationException e){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Username "+user.getUsername()+" exists. Try again."        
            );
        }
    }

    @GetMapping("/traer")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getUsers().stream().map(elem -> 
            new UserDTO(
                elem.getIdUser(),
                elem.getNombre(),
                elem.getApellido(),
                elem.getUsername()
            )
        ).collect(Collectors.toList()));
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<User> getUser(@PathVariable Long idUser) {
        User user = userService.getUser(idUser);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/eliminar/{idUser}")
    public ResponseEntity<User> deleteUser(@PathVariable Long idUser) {
        User userDeleted = userService.deleteUser(idUser);
        if (userDeleted == null) {
            return new ResponseEntity<User>(userDeleted, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(userDeleted, HttpStatus.ACCEPTED);
    }   

    @PutMapping("/editar/{idUser}")
    public ResponseEntity<User> updateUser(@PathVariable Long idUser, @RequestBody User user) {
        User userToUpdate = userService.getUser(idUser);
        if (userToUpdate == null) {
            return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
        } else {
            user.setIdUser(idUser);
            userService.updateUser(user);
            return ResponseEntity.ok(userService.getUser(user.getIdUser()));
        }
    }
}
