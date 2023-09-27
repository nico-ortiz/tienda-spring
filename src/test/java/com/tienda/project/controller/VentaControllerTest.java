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
import com.tienda.project.model.Role;
import com.tienda.project.model.User;
import com.tienda.project.model.Venta;
import com.tienda.project.service.ProductoService;
import com.tienda.project.service.UserService;
import com.tienda.project.service.VentaService;

@WebMvcTest(VentaController.class)
public class VentaControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VentaService ventaService;

    @MockBean
    private UserService userService;

    @MockBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createVentaAPI() throws Exception {
        User c1 = new User(
            1L,
            "Ronaldo",
            "Nazario",
            "123123123",
            "",
            Role.USER
        );

        Venta venta = new Venta(
            LocalDate.of(2023, 9, 06),
            26000.0,
            c1
        );
        when(ventaService.createVenta(any(Venta.class))).thenReturn(venta);
        
        mockMvc.perform(post("/ventas/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(venta)))
                    .andExpect(status().isCreated());
    }

    @Test
    void cantCreateVentaAPI() throws Exception {
        User c1 = new User(
            1L,
            "Ronaldo",
            "Nazario",
            "123123123",
            "",
            Role.USER
        );

        Venta venta = new Venta(
            2L,
            LocalDate.of(2023, 9, 06),
            26000.0,
            c1
        );
        when(ventaService.createVenta(venta)).thenReturn(null);
        
        mockMvc.perform(post("/ventas/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(venta)))
                    .andExpect(status().isNotFound());
    }

    @Test
    void deleteVentaAPI() throws Exception {
        Venta venta = new Venta(
            2L,
            LocalDate.of(2023, 9, 06),
            26000.0,
            new User()
        );
        when(ventaService.deleteVenta(anyLong())).thenReturn(venta);
        mockMvc.perform(delete("/ventas/eliminar/{codigoVenta}", 2L))
                .andExpect(status().isAccepted());        
    }

    @Test
    void cantDeleteVentaAPI() throws Exception {
        when(ventaService.deleteVenta(anyLong())).thenReturn(null);
        mockMvc.perform(delete("/ventas/eliminar/{codigoVenta}", 2L))
                .andExpect(status().isNotFound());        
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
        when(ventaService.getVenta(anyLong())).thenReturn(v1);
        when(ventaService.getProductosByAVenta(v1.getCodigoVenta())).thenReturn(v1.getListaProductos());

        mockMvc.perform(get("/ventas/productos/{codigoVenta}", v1.getCodigoVenta()))
                .andExpect(status().isOk());
    }

    @Test
    void cantGetProductosByAVentaAPI() throws Exception {
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
        when(ventaService.getVenta(anyLong())).thenReturn(null);

        mockMvc.perform(get("/ventas/productos/{codigoVenta}", v1.getCodigoVenta()))
                .andExpect(status().isNotFound());
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
            1L,
            "Osvaldo",
            "Ardiles",
            "14443322"
        );

        Venta ventaToUpdate = new Venta(
            23L,
            LocalDate.of(1988, 12, 16),
            6000.0,
            cliente
        );

        Venta venta = new Venta(
            23L,
            LocalDate.of(1918, 02, 16),
            4000.0,
            cliente
        );
        when(ventaService.getVenta(anyLong())).thenReturn(ventaToUpdate);
        when(userService.getUser(anyLong())).thenReturn(cliente);
        when(ventaService.updateVenta(anyLong(), any(Venta.class))).thenReturn(ventaToUpdate);
        when(ventaService.createVenta(any(Venta.class))).thenReturn(ventaToUpdate);
        when(ventaService.getVenta(anyLong())).thenReturn(venta);
        
        mockMvc.perform(put("/ventas/editar/{codigoVenta}", 23L, venta)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(venta)))
                    .andExpect(status().isOk());
    }

        @Test
    void cantUpdateNotExistedVentaAPI() throws Exception {
        User cliente = new User(
            1L,
            "Osvaldo",
            "Ardiles",
            "14443322"
        );

        Venta venta = new Venta(
            23L,
            LocalDate.of(1918, 02, 16),
            4000.0,
            cliente
        );
        when(ventaService.getVenta(anyLong())).thenReturn(null);
        
        mockMvc.perform(put("/ventas/editar/{codigoVenta}", 23L, venta)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(venta)))
                    .andExpect(status().isNotFound());
    }

    @Test
    void cantUpdateVentaNotExistsUserAPI() throws Exception {
        User cliente = new User(
            1L,
            "Osvaldo",
            "Ardiles",
            "14443322"
        );

        Venta ventaToUpdate = new Venta(
            23L,
            LocalDate.of(1988, 12, 16),
            6000.0,
            cliente
        );

        Venta venta = new Venta(
            23L,
            LocalDate.of(1918, 02, 16),
            4000.0,
            cliente
        );
        when(ventaService.getVenta(anyLong())).thenReturn(ventaToUpdate);
        when(userService.getUser(anyLong())).thenReturn(null);
        
        mockMvc.perform(put("/ventas/editar/{codigoVenta}", 23L, venta)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(venta)))
                    .andExpect(status().isNotFound());
    }

}
