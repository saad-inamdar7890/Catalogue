// src/main/java/com/application/catalogue/Product/ProductId.java
package com.application.catalogue.Product;

import java.io.Serializable;
import java.util.Objects;

public class ProductId implements Serializable {

    private String article;
    private String colour;

    // Default constructor
    public ProductId() {}

    // Parameterized constructor
    public ProductId(String article, String colour) {
        this.article = article;
        this.colour = colour;
    }

    // Getters and setters
    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductId productId = (ProductId) o;
        return Objects.equals(article, productId.article) && Objects.equals(colour, productId.colour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(article, colour);
    }
}