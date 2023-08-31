package com.tienda.project.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tienda.project.dao.IClienteRepository;
import com.tienda.project.model.Cliente;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {
    
    @Mock
    private IClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService underTest;

    @Test
    void canCreateCliente() {
        //Arrange    
        Cliente c1 = new Cliente(
            "Ronaldo",
            "Nazario",
            "23121997"
        );   
        when(clienteRepository.save(any(Cliente.class))).thenReturn(c1);

        //Act
        Cliente clienteSaved = underTest.createCliente(c1);
        
        //Assert
        assertThat(clienteSaved).isEqualTo(c1);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void canGetCliente() {
        //Arrange
        Cliente c2 = new Cliente(
            "Lionel",
            "Messi",
            "06122009"
        );
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(c2));

        //Act
        //idCliente should be random
        Cliente cliente = underTest.getCliente(1L);

        //Assert
        assertThat(cliente).usingRecursiveComparison().isEqualTo(c2);
        verify(clienteRepository, times(1)).findById(anyLong());
    }

    @Test
    void canGetAllClientes() {
        //Arrange
        when(clienteRepository.findAll()).thenReturn((List.of(new Cliente(), new Cliente())));
        
        //Act        
        //Assert
        assertThat(underTest.getClientes()).hasSize(2);
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void canUpdateCliente() {
        //Arrange
        Cliente c3 = new Cliente(
            "Zinedine",
            "Zidane",
            "22121998"
        );
        when(clienteRepository.save(any(Cliente.class))).thenReturn(c3);
        
        //Act
        Cliente clienteUpdated = underTest.updateCliente(1L, c3);
        
        //Assert
        assertThat(clienteUpdated).isNotNull();
        assertThat(clienteUpdated).isEqualTo(c3);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void canDeleteCliente() {
        //Arrange
        doNothing().when(clienteRepository).deleteById(anyLong());

        //Act and Assert
        //idCliente should be random or the id of a client
        underTest.deleteCliente(1L);    
        verify(clienteRepository, times(1)).deleteById(anyLong());
    }
}