package com.ikea.warehouse.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Prachi Das
 */
@Getter
@Setter
public class Product {

    private String name;
    private List<Article> containArticles;
}
