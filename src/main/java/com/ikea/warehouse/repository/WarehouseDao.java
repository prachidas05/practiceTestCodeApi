package com.ikea.warehouse.repository;

import com.ikea.warehouse.entity.BillOfMaterialEntity;
import com.ikea.warehouse.entity.InventoryEntity;
import com.ikea.warehouse.entity.ProductEntity;

import java.util.List;

/**
 * @author Prachi Das
 */
public interface WarehouseDao {

    void addUpdateArticles(InventoryEntity entity);

    void addProducts(ProductEntity entity);

    List<String> retrieveAllProducts();

    InventoryEntity getInventoryEntity(Long artId);

    void addBillOfMaterials(BillOfMaterialEntity bomEntity);

    List<BillOfMaterialEntity> getBillOfMaterialEntity(String productName);
}
