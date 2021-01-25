package com.ikea.warehouse.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ikea.warehouse.entity.BillOfMaterialEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Model class for BOM. JSON representation of this class would look like:
 *
 * <pre>
 {"bom": [
 {
 "productName": "Dining Chair",
 "articleId": "1",
 "amountOf": "4"
 },
 {
 "productName": "Dining Chair",
 "articleId": "2",
 "amountOf": "8"
 },
 {
 "productName": "Dining Chair",
 "articleId": "3",
 "amountOf": "1"
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
 @JsonInclude
    public class BillOfMaterialRequest {

        /**
         * Property <em>customerStatements</em>
         */
        @NotNull
        @Valid
        private List<BillOfMaterialEntity> bom;

}

