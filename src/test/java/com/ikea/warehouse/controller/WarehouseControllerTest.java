package com.ikea.warehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikea.warehouse.WarehouseApplication;
import com.ikea.warehouse.model.InventoryRequest;
import com.ikea.warehouse.model.ProductOverviewResponse;
import com.ikea.warehouse.model.ProductRequest;
import com.ikea.warehouse.service.WarehouseService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.ikea.warehouse.util.TestUtils.asJsonString;
import static com.ikea.warehouse.util.TestUtils.readFile;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Prachi Das
 */
@SpringBootTest(classes= WarehouseApplication.class)
@AutoConfigureMockMvc
class WarehouseControllerTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private WarehouseService warehouseService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testResponse_returnsStatusOKForAddArticles() throws Exception {
        final InventoryRequest request = mapper.readValue(readFile("inventory-test-data-1.json"), InventoryRequest.class);
        doNothing().when(warehouseService).addArticles(request);
        mockMvc.perform(
                post("/articles").contentType(MediaType.APPLICATION_JSON_VALUE).content(asJsonString(request)))
                .andExpect(status().isOk());
        verify(warehouseService, times(1)).addArticles(Mockito.any(InventoryRequest.class));

    }

    @Test
    void testResponse_returnsStatusOKForAddProducts() throws Exception {
        final ProductRequest request = mapper.readValue(readFile("product-test-data-1.json"), ProductRequest.class);
        doNothing().when(warehouseService).addProducts(request);
        mockMvc.perform(
                post("/products").contentType(MediaType.APPLICATION_JSON_VALUE).content(asJsonString(request)))
                .andExpect(status().isOk());
        verify(warehouseService, times(1)).addProducts(Mockito.any(ProductRequest.class));

    }
    @Test
    void testResponse_returnsStatusOKForGetAllProducts() throws Exception {
        final ProductOverviewResponse response = mapper.readValue(readFile("product-overview-test-response.json"),
                ProductOverviewResponse.class);
        when(warehouseService.getAllProducts()).thenReturn(response);
        mockMvc.perform(
                get("/products/overview")).andExpect(status().isOk());
        verify(warehouseService, times(1)).getAllProducts();

    }
    @Test
    void testResponse_returnsStatusOKForRemoveProduct() throws Exception {
        when(warehouseService.removeProduct(anyString(),anyLong())).thenReturn("Success");
        mockMvc.perform(post("/product/sold?name=Dining Chair&quantity=1").
                contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andExpect(content().string("Success"));
        verify(warehouseService, times(1)).removeProduct("Dining Chair",1L);

    }
}
