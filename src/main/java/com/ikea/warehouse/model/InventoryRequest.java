package com.ikea.warehouse.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ikea.warehouse.entity.InventoryEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Model class for Inventory. JSON representation of this class would look like:
 *
 * <pre>
 {
 "inventory": [
 {
 "articleId": "1",
 "articleName": "leg",
 "stock": "12"
 },
 {
 "articleId": "2",
 "articleName": "screw",
 "stock": "17"
 },
 {
 "articleId": "3",
 "articleName": "seat",
 "stock": "2"
 },
 {
 "articleId": "4",
 "articleName": "table top",
 "stock": "1"
 }
 ]
 }

 * </pre>
 *
 * @author Prachi Das
 *
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InventoryRequest {

    /**
     * Property <em>inventory</em>
     */
    @NotNull
    @Valid
    private List<InventoryEntity> inventory;

}
