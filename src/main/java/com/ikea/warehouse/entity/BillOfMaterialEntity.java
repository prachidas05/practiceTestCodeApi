package com.ikea.warehouse.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Prachi Das
 */
@Getter
@Setter
@Entity
@Table(name = "BILL_OF_MATERIAL")
public class BillOfMaterialEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "BOM_ID")
    private int bomId;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "ARTICLE_ID")
    private Long articleId;

    @Column(name = "AMOUNT_OF")
    private Long amountOf;

}
