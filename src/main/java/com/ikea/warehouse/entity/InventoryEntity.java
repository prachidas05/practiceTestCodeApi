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
@Table(name = "INVENTORY")
public class InventoryEntity implements Serializable {

    @Id
    @Column(name = "ARTICLE_ID")
    private Long articleId;

    @Column(name = "ARTICLE_NAME")
    private String articleName;

    @Column(name = "STOCK")
    private Long stock;

}
