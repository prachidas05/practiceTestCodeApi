package com.ikea.warehouse.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikea.warehouse.entity.BillOfMaterialEntity;
import com.ikea.warehouse.entity.InventoryEntity;
import com.ikea.warehouse.entity.ProductEntity;
import com.ikea.warehouse.model.BillOfMaterialRequest;
import com.ikea.warehouse.model.InventoryRequest;
import com.ikea.warehouse.model.ProductOverviewResponse;
import com.ikea.warehouse.model.ProductRequest;
import com.ikea.warehouse.repository.WarehouseDaoImpl;
import com.ikea.warehouse.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * @author Prachi Das
 */
@SpringBootTest
 class WarehouseServiceImplTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    private final WarehouseService warehouseService = new WarehouseServiceImpl();

    @Mock
    private WarehouseDaoImpl warehouseDao;

    @BeforeEach
     void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testAddArticles_ShouldCallDAOMethodsToAddNewArticles() throws IOException {
        doNothing().when(warehouseDao).addUpdateArticles(Mockito.any(InventoryEntity.class));
        warehouseService.addArticles(getArticleRequest());
        verify(warehouseDao, Mockito.times(1)).addUpdateArticles(Mockito.any(InventoryEntity.class));
    }

    @Test
     void testAddProducts_ShouldCallDAOMethodsToAddNewProducts() throws IOException {
        doNothing().when(warehouseDao).addProducts(Mockito.any(ProductEntity.class));
        warehouseService.addProducts(getProductRequest());
        verify(warehouseDao, Mockito.times(1)).addProducts(Mockito.any(ProductEntity.class));
    }

    private ProductRequest getProductRequest() throws IOException {
        ProductRequest  request = mapper.readValue(TestUtils.readFile("product-test-data-1.json"),ProductRequest.class);
        return request;

    }

    private InventoryRequest getArticleRequest() throws IOException {
        InventoryRequest  request = mapper.readValue(TestUtils.readFile("inventory-test-data-1.json"),InventoryRequest.class);
        return request;
    }

    @Test
     void testGetAllProducts_ShouldReturnAllProducts() throws IOException {

        when(warehouseDao.retrieveAllProducts()).thenReturn(mockAllProducts());
        when(warehouseDao.getBillOfMaterialEntity(Mockito.eq("Dining Chair"))).thenReturn(mockBOMEntity("Dining Chair"));
        when(warehouseDao.getBillOfMaterialEntity(Mockito.eq("Dinning Table"))).thenReturn(mockBOMEntity("Dinning Table"));
        when(warehouseDao.getInventoryEntity(Mockito.eq(1L))).thenReturn(mockInventoryEntity(1L));
        when(warehouseDao.getInventoryEntity(Mockito.eq(2L))).thenReturn(mockInventoryEntity(2L));
        when(warehouseDao.getInventoryEntity(Mockito.eq(3L))).thenReturn(mockInventoryEntity(3L));
        when(warehouseDao.getInventoryEntity(Mockito.eq(4L))).thenReturn(mockInventoryEntity(4L));
        final ProductOverviewResponse response = warehouseService.getAllProducts();
        assertNotNull(response);
        assertNotNull(response.getProducts());
        assertEquals(2, response.getProducts().size());
        assertEquals(2,response.getProducts().get(0).getQuantity());
        assertEquals(1,response.getProducts().get(1).getQuantity());
    }

    @Test
     void testRemoveProduct_ShouldUpdateInventoryWhenStockIsAvailable() throws IOException {
        when(warehouseDao.getBillOfMaterialEntity(Mockito.eq("Dining Chair"))).thenReturn(mockBOMEntity("Dining Chair"));
        when(warehouseDao.getInventoryEntity(Mockito.eq(1L))).thenReturn(mockInventoryEntity(1L));
        when(warehouseDao.getInventoryEntity(Mockito.eq(2L))).thenReturn(mockInventoryEntity(2L));
        when(warehouseDao.getInventoryEntity(Mockito.eq(3L))).thenReturn(mockInventoryEntity(3L));
        doNothing().when(warehouseDao).addUpdateArticles(Mockito.any(InventoryEntity.class));
        String message = warehouseService.removeProduct("Dining Chair",1L);
        assertNotNull(message);
        assertEquals("Success", message);
    }

    @Test
     void testRemoveProduct_ShouldReturnOutOfStockWhenNoStockAvailable() throws IOException {
        when(warehouseDao.getBillOfMaterialEntity(Mockito.eq("Dining Chair"))).thenReturn(mockBOMEntity("Dining Chair"));
        when(warehouseDao.getInventoryEntity(Mockito.eq(1L))).thenReturn(mockInventoryEntity(1L));
        when(warehouseDao.getInventoryEntity(Mockito.eq(2L))).thenReturn(mockInventoryEntity(2L));
        when(warehouseDao.getInventoryEntity(Mockito.eq(3L))).thenReturn(mockInventoryEntity(3L));
        doNothing().when(warehouseDao).addUpdateArticles(Mockito.any(InventoryEntity.class));
        String message = warehouseService.removeProduct("Dining Chair",4L);
        assertNotNull(message);
        assertEquals("Out of Stock", message);
    }

    private InventoryEntity mockInventoryEntity(Long id) {
        InventoryEntity entity1 = new InventoryEntity();
        entity1.setArticleId(id);
        if(id == 1L){
            entity1.setArticleName("leg");
            entity1.setStock(12L);
        }else if(id == 2L){
            entity1.setArticleName("screw");
            entity1.setStock(17L);
        }else if(id == 3L){
            entity1.setArticleName("seat");
            entity1.setStock(2L);
        }else if(id == 4L){
            entity1.setArticleName("table top");
            entity1.setStock(1L);
        }

        return entity1;
    }

    private List<BillOfMaterialEntity> mockBOMEntity(String productName) throws IOException {
        BillOfMaterialRequest request = new BillOfMaterialRequest();
        if(productName.equalsIgnoreCase("Dining Chair")){
             request = mapper.readValue(TestUtils.readFile("bill-of-material-testdata-1.json"),BillOfMaterialRequest.class);
        } else if(productName.equalsIgnoreCase("Dinning Table")){
            request = mapper.readValue(TestUtils.readFile("bill-of-material-testdata-2.json"),BillOfMaterialRequest.class);
         }
        return request.getBom();
    }

    private List<String> mockAllProducts() {
        List<String> allProducts = new ArrayList();
        allProducts.add("Dining Chair");
        allProducts.add("Dinning Table");
        return  allProducts;
    }


}
