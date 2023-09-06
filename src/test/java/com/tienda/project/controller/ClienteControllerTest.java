package com.tienda.project.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tienda.project.model.Cliente;
import com.tienda.project.service.ClienteService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClienteService clienteService;

    @Test
    void createClienteAPI() throws Exception {
        Cliente cliente = new Cliente(
            "Ronaldo",
            "Nazario",
            "12131998"
        );
        when(clienteService.createCliente(cliente)).thenReturn(cliente);

        mockMvc.perform(post("/clientes/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteClienteAPI() throws Exception {
        when(clienteService.deleteCliente(anyLong())).thenReturn(any(Cliente.class));
        mockMvc.perform(delete("/clientes/eliminar/{idCliente}", 1L))
                .andExpect(status().isAccepted());
    }

    @Test
    void getClienteAPI() throws Exception{
        Cliente cliente = new Cliente("Nicolas", "Ortiz", "12222122");
        when(clienteService.getCliente(1L)).thenReturn(cliente);

        mockMvc.perform(get("/clientes/{idCliente}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void getClientesAPI() throws Exception{
        when(clienteService.getClientes()).thenReturn(List.of(new Cliente(), new Cliente()));

        mockMvc.perform(get("/clientes/traer"))
                .andExpect(status().isOk());
    }

    @Test
    void updateClienteAPI() throws Exception{
        Cliente clienteUpdated = new Cliente(
            "Zinedine",
            "Zidane",
            "1234444232"
        );

        when(clienteService.updateCliente(2L, clienteUpdated)).thenReturn(clienteUpdated);

        mockMvc.perform(put("/clientes/editar/{idCliente}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(clienteUpdated)))
                .andExpect(status().isOk());

    }
}
