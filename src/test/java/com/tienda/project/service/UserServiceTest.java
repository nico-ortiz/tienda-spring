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
    void canCreateUser() {
        //Arrange    
        User c1 = new User(
            "Ronaldo",
            "Nazario",
            "23121997"
        );   
        when(userRepository.save(any(User.class))).thenReturn(c1);

        //Act
        User userSaved = underTest.createUser(c1);
        
        //Assert
        assertThat(userSaved).isEqualTo(c1);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void canGetUser() {
        //Arrange
        User c2 = new User(
            "Lionel",
            "Messi",
            "06122009"
        );
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(c2));

        //Act
        //idUser should be random
        User User = underTest.getUser(1L);

        //Assert
        assertThat(User).usingRecursiveComparison().isEqualTo(c2);
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void canGetAllUsers() {
        //Arrange
        when(userRepository.findAll()).thenReturn((List.of(new User(), new User())));
        
        //Act        
        //Assert
        assertThat(underTest.getUsers()).hasSize(2);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void canUpdateUser() {
        //Arrange
        User c3 = new User(
            "Zinedine",
            "Zidane",
            "22121998"
        );
        when(userRepository.save(any(User.class))).thenReturn(c3);
        
        //Act
        User userUpdated = underTest.updateUser(c3);
        
        //Assert
        assertThat(userUpdated).isNotNull();
        assertThat(userUpdated).isEqualTo(c3);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void canDeleteUser() {
        //Arrange
        doNothing().when(userRepository).deleteById(anyLong());

        //Act and Assert
        //idUser should be random or the id of a client
        underTest.deleteUser(1L);    
        verify(userRepository, times(1)).deleteById(anyLong());
    }
}