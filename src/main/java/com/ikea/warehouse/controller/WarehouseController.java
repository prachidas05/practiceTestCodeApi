package com.ikea.warehouse.controller;

import com.ikea.warehouse.model.InventoryRequest;
import com.ikea.warehouse.model.ProductOverviewResponse;
import com.ikea.warehouse.model.ProductRequest;
import com.ikea.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Prachi Das
 */
@RestController
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    /**
     * add articles to the Inventory as in inventory.json
     * @param request {@link com.ikea.warehouse.model.InventoryRequest} instance
     * @return ResponseEntity
     */
    @PostMapping(value = "/articles", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> response(
            @Valid @RequestBody @NotNull final InventoryRequest request){
        warehouseService.addArticles(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * add products to the warehouse as in products.json which acts as master data for the warehouse
     * @param request {@link com.ikea.warehouse.model.ProductRequest} instance
     * @return ResponseEntity
     */
    @PostMapping(value = "/products", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addProducts(
            @Valid @RequestBody @NotNull final ProductRequest request){
        warehouseService.addProducts(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * Get all products and quantity of each that is an available with the current inventory
     * @return ResponseEntity {@link ProductOverviewResponse} instance
    **/
    @GetMapping(value = "/products/overview", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductOverviewResponse> getAllProducts() {
        ProductOverviewResponse response = new ProductOverviewResponse();
        response = warehouseService.getAllProducts();
        return new ResponseEntity(response, HttpStatus.OK);
    }

    /**
     * Remove(Sell) a product and update the inventory accordingly
     * @param name {@link String} name of the product to be sold
     * @param quantity {@link Long} quantity of the product to be sold
     * @return ResponseEntity
    */

    @PostMapping("/product/sold")
    public ResponseEntity<String> removeProduct(@RequestParam("name") final String name, @RequestParam("quantity") final Long quantity) {
        String message = warehouseService.removeProduct(name,quantity);
        return new ResponseEntity( message,HttpStatus.OK);
    }

}
