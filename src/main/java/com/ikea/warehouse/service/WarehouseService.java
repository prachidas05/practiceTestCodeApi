package com.ikea.warehouse.service;

import com.ikea.warehouse.model.InventoryRequest;
import com.ikea.warehouse.model.ProductOverviewResponse;
import com.ikea.warehouse.model.ProductRequest;

/**
 * @author Prachi Das
 */
public interface WarehouseService {

    void addArticles(InventoryRequest request);

    void addProducts(ProductRequest request);

    ProductOverviewResponse getAllProducts();

    String removeProduct(String productName, Long quantity);
}
