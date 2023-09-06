package com.tienda.project.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tienda.project.model.Producto;
import com.tienda.project.service.ProductoService;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductoService productoService;

    @Test
    void createProductoAPI() throws Exception {
        Producto producto = new Producto(
            "Camiseta Argentina",
            "Adidas",
            30000.0,
            120.0
        );
        when(productoService.createProducto(producto)).thenReturn(producto);

        mockMvc.perform(post("/productos/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteProductAPI() throws Exception {
        when(productoService.deleteProducto(anyLong())).thenReturn(any(Producto.class));
        mockMvc.perform(delete("/productos/eliminar/{codigoProducto}", 1L))
                .andExpect(status().isAccepted());
    }

    @Test
    void getProductoAPI() throws Exception {
        Producto producto = new Producto(
            1L,
            "Zapatillas",
            "Nike",
            30000.0,
            12.0
        );
        when(productoService.getProducto(1L)).thenReturn(producto);

        mockMvc.perform(get("/productos/{codigoProducto}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void getProductosAPI() throws Exception {
        when(productoService.getProductos()).thenReturn(List.of(new Producto(), new Producto()));

        mockMvc.perform(get("/productos/traer"))
                .andExpect(status().isOk());
    }

    @Test
    void getProductosStockAPI() throws Exception {
        Producto p1 = new Producto(
            "Camiseta Argentina",
            "Adidas",
            30000.0,
            120.0
        );
        Producto p2 = new Producto(
            1L,
            "Zapatillas",
            "Nike",
            30000.0,
            2.0
        );
        
        when(productoService.getProductosWhoseStockLessThanFive()).thenReturn(List.of(p2));

        mockMvc.perform(get("/productos/falta_stock"))
                .andExpect(status().isOk());
    }

    @Test
    void updateProductoAPI() throws Exception{
        Producto p1 = new Producto(
            1L,
            "Camiseta Argentina",
            "Adidas",
            20000.0,
            20.0
        );
        when(productoService.updateProducto(p1.getCodigoProducto(), p1)).thenReturn(p1);

        mockMvc.perform(put("/productos/editar/{codigoProducto}", p1.getCodigoProducto())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(p1)))
                .andExpect(status().isOk());
            
    }


}
