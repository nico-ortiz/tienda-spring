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

import com.tienda.project.dao.IProductoRepository;
import com.tienda.project.model.Producto;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {
    
    @Mock
    private IProductoRepository productoRepository;

    @InjectMocks
    private ProductoService underTest;

    @Test
    void canCreateProducto() {
        //Arrange
        Producto p1 = new Producto (
            "Camiseta Argentina",
            "Adidas",
            30000.0,
            90.0
        );
        when(productoRepository.save(any(Producto.class))).thenReturn(p1);
        
        //Act
        Producto expected = underTest.createProducto(p1);

        //Assert
        assertThat(p1).isEqualTo(expected);
        verify(productoRepository, times(1)).save(any(Producto.class));
    }
    
    @Test
    void canGetAllProductos() {
        //Arrange
        when(productoRepository.findAll()).thenReturn(List.of(new Producto(), new Producto()));

        //Act
        List<Producto> expectedProductos = underTest.getProductos();
        
        //Assert
        assertThat(expectedProductos).hasSize(2);
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void canGetProducto() {
        //Arrange
        Producto p2 = new Producto (
            "Camiseta Instituto",
            "Lyon",
            20000.0,
            45.0
        );
        
        when(productoRepository.findById(anyLong())).thenReturn(Optional.of(p2));

        //Act
        Producto expectedProducto = underTest.getProducto(1L);

        //Assert
        assertThat(p2).isEqualTo(expectedProducto);
        verify(productoRepository, times(1)).findById(anyLong());
    }

    @Test
    void canUpdateProducto() {
        //Arrange
        Producto p3 = new Producto (
            "Camiseta Chelsea",
            "Nike",
            23000.0,
            23.0
        );
        when(productoRepository.save(any(Producto.class))).thenReturn(p3);

        //Act
        Producto expectedProducto = underTest.updateProducto(p3);

        //Assert
        assertThat(expectedProducto).isNotNull();
        assertThat(p3).isEqualTo(expectedProducto);
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void canDeleteProducto() {
        //Arrange
        doNothing().when(productoRepository).deleteById(anyLong());

        //Act
        underTest.deleteProducto(1L);

        //Assert
        verify(productoRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void canGetProductosWhoseStockLessThanFive() {
        //Arrange
        Producto p1 = new Producto(
            1L,
            "Zapatillas",
            "Adidas",
            20000.0,
            120.0
        );
        Producto p2 = new Producto(
            1L,
            "Campera",
            "Adidas",
            23000.0,
            4.0
        );
        when(productoRepository.findAll()).thenReturn(List.of(p1, p2));

        //Act
        List<Producto> productos = underTest.getProductosWhoseStockLessThanFive();

        //Assert
        assertThat(productos.size()).isEqualTo(1);
        assertThat(productos.get(0).getCantidadDisponible()).isLessThan(5);
    }
}
