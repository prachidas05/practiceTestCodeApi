package com.ikea.warehouse.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Prachi Das
 */
@Getter
@Setter
@Entity
@Table(name = "PRODUCT")
public class ProductEntity implements Serializable {

    @Id
    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "PRODUCT_PRICE")
    private Double productPrice;

}
