package com.tienda.project.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tienda.project.model.User;
import com.tienda.project.service.UserService;
import com.tienda.project.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void createUserAPI() throws Exception {
        User user = new User(
            "Ronaldo",
            "Nazario",
            "12131998"
        );
        when(userService.createUser(user)).thenReturn(user);

        mockMvc.perform(post("/users/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteUserAPI() throws Exception {
        when(userService.deleteUser(anyLong())).thenReturn(any(User.class));
        mockMvc.perform(delete("/users/eliminar/{idUser}", 1L))
                .andExpect(status().isAccepted());
    }

    @Test
    void getUserAPI() throws Exception{
        User User = new User("Nicolas", "Ortiz", "12222122");
        when(userService.getUser(1L)).thenReturn(User);

        mockMvc.perform(get("/users/{idUser}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void getUsersAPI() throws Exception{
        when(userService.getUsers()).thenReturn(List.of(new User(), new User()));

        mockMvc.perform(get("/users/traer"))
                .andExpect(status().isOk());
    }

    @Test
    void updateUserAPI() throws Exception{
        User userUpdated = new User(
            "Zinedine",
            "Zidane",
            "1234444232"
        );

        when(userService.updateUser(2L, userUpdated)).thenReturn(userUpdated);

        mockMvc.perform(put("/users/editar/{idUser}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userUpdated)))
                .andExpect(status().isOk());

    }
}
