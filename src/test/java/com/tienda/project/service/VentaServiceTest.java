package com.tienda.project.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tienda.project.additionalFunctions.Pair;
import com.tienda.project.dao.IProductoRepository;
import com.tienda.project.dao.IUserRepository;
import com.tienda.project.dao.IVentaRepository;
import com.tienda.project.dto.VentaDTO;
import com.tienda.project.model.User;
import com.tienda.project.model.Producto;
import com.tienda.project.model.Venta;

@ExtendWith(MockitoExtension.class)
public class VentaServiceTest {
    
    @Mock
    private IVentaRepository ventaRepository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IProductoRepository productoRepository;

    @InjectMocks
    private VentaService underTest;

    @Test
    void canCreateVenta() {
        //Arrange
        User c1 = new User(
            1L,
            "Lionel",
            "Messi",
            "06122009"
        );

        Venta v1 = new Venta(
            1L,
            LocalDate.of(2022, 12, 18),
            0.0,
            c1
        );
        when(ventaRepository.save(any(Venta.class))).thenReturn(v1);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(c1));
        
        //Act
        Venta ventaExpected = underTest.createVenta(v1);

        //Assert
        assertThat(v1).isEqualTo(ventaExpected);
        assertThat(ventaExpected.getListaProductos()).isEmpty();
        assertThat(c1).isEqualTo(ventaExpected.getUser());
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
        User c1 = new User(
            "Lionel",
            "Messi",
            "06122009"
        );

        Venta v1 = new Venta(
            1123L,
            LocalDate.of(2022, 12, 18),
            0.0,
            c1
        );
        when(ventaRepository.findById(anyLong())).thenReturn(Optional.of(v1));

        //Act
        Venta expectedVenta = underTest.getVenta(1L);

        //Assert
        assertThat(v1).isEqualTo(expectedVenta);
        assertThat(c1.getDni()).isEqualTo(expectedVenta.getUser().getDni());
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
            new User (
                1L,
                "Zinedine",
                "Zidane",
                "22121998"
            )
        );
        
        Venta newVenta = new Venta(
            originalVenta.getCodigoVenta(),
            LocalDate.of(2009, 12, 2),
            0.0,
            new User (
                1L,
                "Ronaldo",
                "Nazario",
                "23121997"
            )
        );
        when(ventaRepository.save(any(Venta.class))).thenReturn(newVenta);
        when(userRepository.findById(1L)).thenReturn(Optional.of(newVenta.getUser()));
        
        //Act 
        Venta updatedVenta = underTest.updateVenta(idVenta, newVenta);

        //Assert
        assertThat(newVenta.getUser()).isEqualTo(updatedVenta.getUser());

    }

    @Test
    void canAddProductoToVenta() {
        //Arrange
        Venta venta = new Venta(
            1L,
            LocalDate.of(2018, 02, 02),
            1200.0,
            new User (
                23L,
                "Ronaldo",
                "Nazario",
                "23121997"
            )
        );

        Producto producto = new Producto(
            2L,
            "Camiseta Retro Instituto",
            "Dribbling",
            2500.0,
            10.0
        );

        when(ventaRepository.findById(anyLong())).thenReturn(Optional.of(venta));
        when(userRepository.findById(23L)).thenReturn(Optional.of(venta.getUser()));
        when(productoRepository.findById(anyLong())).thenReturn(Optional.of(producto));
        when(ventaRepository.save(any(Venta.class))).thenReturn(venta);
        
        //Act
        Venta expectedVenta = underTest.addProductoToVenta(1L, 2L);

        //Assert
        assertThat(expectedVenta.getListaProductos()).contains(producto);
        assertThat(expectedVenta.getTotal()).isEqualTo(3700.0);
        assertThat(producto.getCantidadDisponible()).isEqualTo(9.0);
    }

    @Test
    void canAddProductWithNoStockToVenta() {
        //Arrange
        Venta venta = new Venta(
            1L,
            LocalDate.of(2018, 02, 02),
            1200.0,
            new User (
                "Ronaldo",
                "Nazario",
                "23121997"
            )
        );

        Producto producto = new Producto(
            2L,
            "Camiseta Retro Instituto",
            "Dribbling",
            2500.0,
            0.0
        );

        when(ventaRepository.findById(anyLong())).thenReturn(Optional.of(venta));
        when(productoRepository.findById(anyLong())).thenReturn(Optional.of(producto));

        //Act
        Venta expectedVenta = underTest.addProductoToVenta(1L, 2L);

        //Assert
        assertThat(expectedVenta).isNull();
    }

    @Test
    void canDeleteProductoToVenta() {
        Producto producto = new Producto(
            2L,
            "Camiseta Retro Instituto",
            "Diadora",
            30000.0,
            1918.0
        );

        Venta venta = new Venta(
            1L,
            LocalDate.of(2018, 02, 02),
            1200.0,
            new User (
                1L,
                "Daniel",
                "Jimenez",
                "199899"
            )
        );
        venta.setListaProductos(new ArrayList<>(List.of(producto)));
        producto.setCantidadDisponible(producto.getCantidadDisponible() - 1);
        
        when(ventaRepository.findById(anyLong())).thenReturn(Optional.of(venta));
        when(productoRepository.findById(anyLong())).thenReturn(Optional.of(producto));
        when(ventaRepository.save(any(Venta.class))).thenReturn(venta);
        when(userRepository.findById(1L)).thenReturn(Optional.of(venta.getUser()));

        //Act
        Venta ventaExpected = underTest.deleteProductoToVenta(1L, 2L);

        //Assert
        assertThat(producto).isNotIn(ventaExpected.getListaProductos());
        assertThat(producto.getCantidadDisponible()).isEqualTo(1918.0);
    }

    @Test
    void canGetAllProductosFromAVenta() {
        //Arrange
        Venta venta = new Venta(
            1L,
            LocalDate.of(2011, 03, 12),
            2900.0,
            new User (
                "David",
                "Beckam",
                "239899"
            )
        );
        venta.setListaProductos(new ArrayList<>(List.of(new Producto(), new Producto())));
        when(ventaRepository.findById(anyLong())).thenReturn(Optional.of(venta));

        //Act
        List<Producto> listOfProductosExpected = underTest.getProductosByAVenta(1L);

        //Assert
        assertThat(listOfProductosExpected).isNotEmpty();
        assertThat(listOfProductosExpected).hasSize(2);
    }

    @Test
    void canGetTotalPriceAndTotalQuantityOfVentasFromADay() {
        Venta v1 = new Venta(
            1L,
            LocalDate.of(2019, 9, 06), 
            1100.0, 
            new User()
        );
        Venta v2 = new Venta(
            2L,
            LocalDate.of(2019, 4, 06), 
            123.0, 
            new User()
        );
        Venta v3 = new Venta(
            3L,
            LocalDate.of(2019, 9, 06), 
            1230.0, 
            new User()
        );

        when(ventaRepository.findAll()).thenReturn(List.of(v1, v2, v3));

        //Act
        Pair<Double> resultExpected = underTest.getTotalPriceAndTotalCountsOfVentasByADay(LocalDate.of(2019, 9, 06));

        //Assert
        assertThat(resultExpected.getFst()).isEqualTo(2330.0);
        assertThat(resultExpected.getSnd()).isEqualTo(2.0);
    }

    @Test
    void canGetMoreExpensiveVenta() {
        Venta v1 = new Venta(
            1L,
            LocalDate.of(2019, 9, 06), 
            1100.0, 
            new User()
        );
        Venta v2 = new Venta(
            2L,
            LocalDate.of(2019, 4, 06), 
            123.0, 
            new User()
        );
        Venta v3 = new Venta(
            3L,
            LocalDate.of(2019, 9, 06), 
            1230.0, 
            new User()
        );
        Venta v4 = new Venta(
            4L,
            LocalDate.of(2011, 9, 06), 
            2100.0, 
            new User("Cylian", "Murphy", "123456789")
        );

        when(ventaRepository.findAll()).thenReturn(List.of(v1, v2, v3, v4));

        //Act
        VentaDTO ventaDTOExpected = underTest.getMoreExpensiveVenta();

        //Assert
        assertThat(ventaDTOExpected.getCodigoVenta()).isEqualTo(4L);
        assertThat(ventaDTOExpected.getTotal()).isEqualTo(2100.0);
        assertThat(ventaDTOExpected.getApellidoCliente()).isEqualTo("Murphy");
    }
}
