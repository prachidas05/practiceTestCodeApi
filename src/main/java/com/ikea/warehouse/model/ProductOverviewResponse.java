package com.ikea.warehouse.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * Model class for ProductOverviewResponse. JSON representation of this class would look like:
 *
 * <pre>
 {
 "products": [
 {
 "productName": "Dining Chair",
 "quantity": 2
 },
 {
 "productName": "Dinning Table",
 "quantity": 1
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
public class ProductOverviewResponse {

    private List<ProductOverview> products;

}
