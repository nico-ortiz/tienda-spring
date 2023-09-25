package com.tienda.project.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tienda.project.additionalFunctions.Pair;
import com.tienda.project.dto.VentaDTO;
import com.tienda.project.model.Producto;
import com.tienda.project.model.User;
import com.tienda.project.model.Venta;
import com.tienda.project.service.VentaService;

@WebMvcTest(VentaController.class)
public class VentaControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VentaService ventaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addProductoToVentaAPI() throws Exception {
        User c1 = new User(
            "Ronaldo",
            "Nazario",
            "123123123"
        );

        Venta venta = new Venta(
            2L,
            LocalDate.of(2023, 9, 06),
            26000.0,
            c1
        );

        Producto producto = new Producto(
            33L,
            "Arroz",
            "Mas",
            250.0,
            15.0
        );
        when(ventaService.addProductoToVenta(2L, 33L)).thenReturn(venta);

        mockMvc.perform(put("/ventas/{codigoVenta}/listaProductos/{codigoProducto}", venta.getCodigoVenta(), producto.getCodigoProducto())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(venta)))
                    .andExpect(status().isOk());
    }

    @Test
    void shouldntAddProductoToVentaAPI() throws Exception{
        Venta venta = new Venta(
            2L,
            LocalDate.of(2023, 9, 06),
            26000.0,
            new User()
        );

        Producto producto = new Producto(
            33L,
            "Arroz",
            "Mas",
            250.0,
            0.0
        );
        when(ventaService.addProductoToVenta(2L, 33L)).thenReturn(null);
        mockMvc.perform(put("/ventas/{codigoVenta}/listaProductos/{codigoProducto}", venta.getCodigoVenta(), producto.getCodigoProducto())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(venta)))
                    .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void createVentaAPI() throws Exception {
        User c1 = new User(
            "Ronaldo",
            "Nazario",
            "123123123"
        );

        Venta venta = new Venta(
            LocalDate.of(2023, 9, 06),
            26000.0,
            c1
        );
        when(ventaService.createVenta(venta)).thenReturn(venta);
        
        mockMvc.perform(post("/ventas/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(venta)))
                    .andExpect(status().isCreated());
    }

    @Test
    void deleteVentaAPI() throws Exception {
        when(ventaService.deleteVenta(anyLong())).thenReturn(any(Venta.class));
        mockMvc.perform(delete("/ventas/eliminar/{codigoVenta}", 1L))
                .andExpect(status().isAccepted());        
    }

    @Test
    void getMoreExpensiveVentaAPI() throws Exception {
        Venta v2 = new Venta(
            LocalDate.of(2023, 9, 06),
            32000.0,
            new User()
        );
        when(ventaService.getMoreExpensiveVenta()).thenReturn(
            new VentaDTO(v2.getCodigoVenta(), v2.getTotal(), v2.getListaProductos().size(), v2.getUser().getNombre(), v2.getUser().getApellido())
        );

        mockMvc.perform(get("/ventas/mayorVenta"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(32000.0));
    }

    @Test
    void getProductosByAVentaAPI() throws Exception {
        Venta v1 = new Venta(
            1L,
            LocalDate.of(2023, 9, 06),
            26000.0,
            new User()
        );
        Producto p1 = new Producto(
            1L,
            "Camiseta Argentina",
            "Adidas",
            20000.0,
            20.0
        );
        Producto p2 = new Producto(
            1L,
            "Zapatillas",
            "Nike",
            30000.0,
            2.0
        );
        v1.addProductos(new ArrayList<>(List.of(p1, p2)));
        when(ventaService.getProductosByAVenta(v1.getCodigoVenta())).thenReturn(v1.getListaProductos());

        mockMvc.perform(get("/ventas/productos/{codigoVenta}", v1.getCodigoVenta()))
                .andExpect(status().isOk());
    }

    @Test
    void getTotalVentasByADayAPI() throws Exception{
        Venta v1 = new Venta(
            LocalDate.of(2023, 9, 06),
            26000.0,
            new User()
        );

        Venta v2 = new Venta(
            LocalDate.of(2023, 9, 06),
            32000.0,
            new User()
        );
        when(ventaService.getTotalPriceAndTotalCountsOfVentasByADay(LocalDate.of(2023, 9, 06)))
            .thenReturn(new Pair<Double>(v1.getTotal() + v2.getTotal(), 2.0));

        mockMvc.perform(get("/ventas?fechaVenta=2023-09-06"))
                .andExpect(status().isOk());
    }

    @Test
    void getVentaAPI() throws Exception {
        User c1 = new User(
            "Ronaldo",
            "Nazario",
            "123123123"
        );

        Venta venta = new Venta(
            23L,
            LocalDate.of(2023, 9, 06),
            26000.0,
            c1
        );
        when(ventaService.getVenta(23L)).thenReturn(venta);

        mockMvc.perform(get("/ventas/{codigoVenta}", venta.getCodigoVenta()))
                .andExpect(status().isOk());
    }

    @Test
    void getVentasAPI() throws Exception { 
        when(ventaService.getVentas()).thenReturn(List.of(new Venta(), new Venta()));

        mockMvc.perform(get("/ventas/traer"))
                .andExpect(status().isOk());
    }

    @Test
    void updateVentaAPI() throws Exception {
        User cliente = new User(
            "Osvaldo",
            "Ardiles",
            "14443322"
        );

        Venta venta = new Venta(
            13L,
            LocalDate.of(1988, 12, 16),
            6000.0,
            cliente
        );
        when(ventaService.updateVenta(venta.getCodigoVenta(), venta)).thenReturn(venta);

        mockMvc.perform(put("/ventas/editar/{codigoVenta}", venta.getCodigoVenta())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(venta)))
                    .andExpect(status().isOk());
    }
}
