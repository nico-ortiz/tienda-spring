package com.tienda.project.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tienda.project.dao.IClienteRepository;
import com.tienda.project.dao.IProductoRepository;
import com.tienda.project.dao.IVentaRepository;
import com.tienda.project.model.Cliente;
import com.tienda.project.model.Venta;

@ExtendWith(MockitoExtension.class)
public class VentaServiceTest {
    
    @Mock
    private IVentaRepository ventaRepository;

    @Mock
    private IClienteRepository clienteRepository;

    @Mock
    private IProductoRepository productoRepository;

    @InjectMocks
    private VentaService underTest;

    @Test
    void canCreateVenta() {
        //Arrange
        Cliente c1 = new Cliente(
            "Lionel",
            "Messi",
            "06122009"
        );
        when(clienteRepository.save(any(Cliente.class))).thenReturn(c1);
        Cliente c1Expected = clienteRepository.save(c1);

        Venta v1 = new Venta(
            LocalDate.of(2022, 12, 18),
            c1Expected
        );
        when(ventaRepository.save(any(Venta.class))).thenReturn(v1);
        
        //Act
        Venta ventaExpected = underTest.createVenta(v1);

        //Assert
        assertThat(v1).isEqualTo(ventaExpected);
        assertThat(ventaExpected.getListaProductos()).isEmpty();
        verify(ventaRepository, timeout(1)).save(any(Venta.class));
    }

    @Test
    void canGetVentas() {
        //Arrange
        when(ventaRepository.findAll()).thenReturn(List.of(new Venta(), new Venta()));
        
        //Act
        //Assert
        assertThat(underTest.getVentas()).hasSize(2);
        verify(ventaRepository, times(1)).findAll();
    }

    @Test 
    void canGetVenta() {
        //Arrange
        Cliente c1 = new Cliente(
            "Lionel",
            "Messi",
            "06122009"
        );
        when(clienteRepository.save(any(Cliente.class))).thenReturn(c1);
        Cliente c1Expected = clienteRepository.save(c1);

        Venta v1 = new Venta(
            LocalDate.of(2022, 12, 18),
            c1Expected
        );
        when(ventaRepository.findById(anyLong())).thenReturn(Optional.of(v1));

        //Act
        Venta expectedVenta = underTest.getVenta(1L);

        //Assert
        assertThat(v1).isEqualTo(expectedVenta);
        verify(ventaRepository, times(1)).findById(anyLong());
    }

    @Test
    void canDeleteVenta() {
        //Arrange
        doNothing().when(ventaRepository).deleteById(anyLong());

        //Act
        //Assert
        underTest.deleteVenta(1L);
        assertThat(underTest.getVenta(1L)).isNull();
        verify(ventaRepository, times(1)).deleteById(1L);
    }

    @Test
    void canUpdateVenta() {
        //Arrange
        Long idVenta = 1L;
        Venta originalVenta = new Venta(
            idVenta,
            LocalDate.of(2009, 12, 2), 
            0.0,
            new Cliente (
                "Zinedine",
                "Zidane",
                "22121998"
            )
        );
        
        Venta newVenta = new Venta(
            originalVenta.getCodigoVenta(),
            LocalDate.of(2009, 12, 2),
            0.0,
            new Cliente (
                "Ronaldo",
                "Nazario",
                "23121997"
            )
        );
        when(ventaRepository.save(any(Venta.class))).thenReturn(newVenta);
        
        //Act 
        Venta updatedVenta = underTest.updateVenta(idVenta, newVenta);

        //Assert
        assertThat(newVenta.getCliente()).isEqualTo(updatedVenta.getCliente());

    }
}
