package com.ikea.warehouse.service;

import com.ikea.warehouse.entity.BillOfMaterialEntity;
import com.ikea.warehouse.entity.InventoryEntity;
import com.ikea.warehouse.entity.ProductEntity;
import com.ikea.warehouse.model.*;
import com.ikea.warehouse.repository.WarehouseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Prachi Das
 */
@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseDao warehouseDao;

    /**
     * This method add articles from Inventory.json
     * @param request {@link InventoryRequest} instance
     */

    @Override
    public void addArticles(InventoryRequest request) {
        List<InventoryEntity> entities = request.getInventory();
        for (InventoryEntity entity : entities) {
            warehouseDao.addUpdateArticles(entity);
        }

    }

    /**
     * This method add products from Product.json
     * @param request {@link ProductRequest} instance
     */
    @Override
    public void addProducts(ProductRequest request) {
        List<Product> products = request.getProducts();
        for(Product product:products){
            ProductEntity entity = new ProductEntity();
            entity.setProductName(product.getName());
            entity.setProductPrice(0D);//Set the product price if received from the products.json
            warehouseDao.addProducts(entity);
            constructAndSaveBillOfMaterials(product);
        }

    }

    private void constructAndSaveBillOfMaterials(Product product) {
        List<Article> articles = product.getContainArticles();
        for(Article article:articles){
            BillOfMaterialEntity bomEntity = new BillOfMaterialEntity();
            bomEntity.setArticleId(article.getArtId());
            bomEntity.setAmountOf(article.getAmountOf());
            bomEntity.setProductName(product.getName());
            warehouseDao.addBillOfMaterials(bomEntity);
        }
    }

    /**
     * This method retrieves all available products in the inventory
     * @return ProductOverviewResponse {@link ProductOverviewResponse} instance
     */
    @Override
    public ProductOverviewResponse getAllProducts() {
        ProductOverviewResponse response= new ProductOverviewResponse();
        List<String> productList = warehouseDao.retrieveAllProducts();
        List<ProductOverview> productQuantityList = new ArrayList<>();
        for(String productName:productList){
            getProductsFromBOM(productQuantityList, productName);
        }
        response.setProducts(productQuantityList);
        return response;
    }

    private void getProductsFromBOM(List<ProductOverview> productQuantityList, String productName) {
        ProductOverview productOverview = new ProductOverview();
        productOverview.setProductName(productName);
        List<BillOfMaterialEntity> bomEntities = warehouseDao.getBillOfMaterialEntity(productName);
        List<Long> availableProduct = new ArrayList<>();
        for(BillOfMaterialEntity bomEntity:bomEntities) {
            getInventory(availableProduct, bomEntity);
        }
        Long maxProductQtInventory = Collections.min(availableProduct);
        productOverview.setQuantity(maxProductQtInventory);
        productQuantityList.add(productOverview);
    }

    private void getInventory(List<Long> availableProduct, BillOfMaterialEntity bomEntity) {
        InventoryEntity inventoryEntity = warehouseDao.getInventoryEntity(bomEntity.getArticleId());
        Long availableStock = inventoryEntity.getStock();
        Long amountOfArticle = bomEntity.getAmountOf();
        Long subProductFromInventory = availableStock / amountOfArticle;
        availableProduct.add(subProductFromInventory);
    }

    /**
     * This method removes a product for the productName and updates the inventory & product once it is sold
     * @param productName {@link String} instance
     * @param quantity {@link Long} instance
     * @return message {@link String} instance
     */
    @Override
    public String removeProduct(String productName, Long quantity) {
        String message = "Success";
        List<BillOfMaterialEntity> bomEntityList = warehouseDao.getBillOfMaterialEntity(productName);
        InventoryRequest request = new InventoryRequest();
        List<InventoryEntity> inventoryToBeUpdatedList = new ArrayList<>();
        if(bomEntityList != null && !bomEntityList.isEmpty()){
            message = calculateStocksToBeUpdated(quantity, message, bomEntityList, inventoryToBeUpdatedList);
        }
        if(!inventoryToBeUpdatedList.isEmpty()){
            request.setInventory(inventoryToBeUpdatedList);
            addArticles(request);
        }
        return message;
    }

    private String calculateStocksToBeUpdated(Long quantity, String message, List<BillOfMaterialEntity> bomEntityList, List<InventoryEntity> inventoryToBeUpdatedList) {
        for(BillOfMaterialEntity bomEntity: bomEntityList){
            Long articleConsumed = quantity *bomEntity.getAmountOf();
            InventoryEntity existingInventoryEntity = warehouseDao.getInventoryEntity(bomEntity.getArticleId());
            Long currentArticle = existingInventoryEntity.getStock();
            Long updatedStock = currentArticle - articleConsumed;
            if(updatedStock >= 0l){
                updateInventory(existingInventoryEntity, updatedStock, inventoryToBeUpdatedList);
            }else{
                message = "Out of Stock";
                inventoryToBeUpdatedList.clear();
                break;
                    }
            }
        return message;
    }

    private void updateInventory(InventoryEntity existingInventoryEntity, Long updatedStock, List<InventoryEntity> inventoryToBeUpdatedList) {
        InventoryEntity updatedInventoryEntity = new InventoryEntity();
        updatedInventoryEntity.setArticleId(existingInventoryEntity.getArticleId());
        updatedInventoryEntity.setArticleName(existingInventoryEntity.getArticleName());
        updatedInventoryEntity.setStock(updatedStock);
        inventoryToBeUpdatedList.add(updatedInventoryEntity);
    }
}
