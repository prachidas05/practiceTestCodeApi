package com.ikea.warehouse.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Model class for Product. JSON representation of this class would look like:
 *
 * <pre>
 {
 "products": [
 {
 "name": "Dining Chair",
 "containArticles": [
 {
 "artId": "1",
 "amountOf": "4"
 },
 {
 "artId": "2",
 "amountOf": "8"
 },
 {
 "artId": "3",
 "amountOf": "1"
 }
 ]
 },
 {
 "name": "Dinning Table",
 "containArticles": [
 {
 "artId": "1",
 "amountOf": "4"
 },
 {
 "artId": "2",
 "amountOf": "8"
 },
 {
 "artId": "4",
 "amountOf": "1"
 }
 ]
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
public class ProductRequest {

    @NotNull
    @Valid
    private List<Product> products;
}
