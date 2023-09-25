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

import com.tienda.project.dao.IUserRepository;
import com.tienda.project.model.User;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserService underTest;

    @Test
    void canCreateCliente() {
        //Arrange    
        User c1 = new User(
            "Ronaldo",
            "Nazario",
            "23121997"
        );   
        when(userRepository.save(any(User.class))).thenReturn(c1);

        //Act
        User clienteSaved = underTest.createUser(c1);
        
        //Assert
        assertThat(clienteSaved).isEqualTo(c1);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void canGetCliente() {
        //Arrange
        User c2 = new User(
            "Lionel",
            "Messi",
            "06122009"
        );
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(c2));

        //Act
        //idCliente should be random
        User cliente = underTest.getUser(1L);

        //Assert
        assertThat(cliente).usingRecursiveComparison().isEqualTo(c2);
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void canGetAllClientes() {
        //Arrange
        when(userRepository.findAll()).thenReturn((List.of(new User(), new User())));
        
        //Act        
        //Assert
        assertThat(underTest.getUsers()).hasSize(2);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void canUpdateCliente() {
        //Arrange
        User c3 = new User(
            "Zinedine",
            "Zidane",
            "22121998"
        );
        when(userRepository.save(any(User.class))).thenReturn(c3);
        
        //Act
        User clienteUpdated = underTest.updateUser(1L, c3);
        
        //Assert
        assertThat(clienteUpdated).isNotNull();
        assertThat(clienteUpdated).isEqualTo(c3);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void canDeleteCliente() {
        //Arrange
        doNothing().when(userRepository).deleteById(anyLong());

        //Act and Assert
        //idCliente should be random or the id of a client
        underTest.deleteUser(1L);    
        verify(userRepository, times(1)).deleteById(anyLong());
    }
}