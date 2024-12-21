package com.application.catalogue.Product;

import java.io.Serializable;
import java.util.Objects;

public class ProductId implements Serializable {

    private String article;
    private String colour;

    public ProductId() {}

    public ProductId(String article, String colour) {
        this.article = article;
        this.colour = colour;
    }

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