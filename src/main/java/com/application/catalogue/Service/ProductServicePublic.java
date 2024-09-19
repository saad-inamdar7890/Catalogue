package com.application.catalogue.Service;

import com.application.catalogue.Product.Product;

import java.util.List;

public interface ProductServicePublic {
    List<Product> getAllProducts();
    void createProduct(Product product);

    String deleteProduct(String Article);

    Product updateProduct(Product product, String Article);

    List<Product> getTrends();

    public List<Product> getProductsRegisteredWithin7Days();


    public List<Product> getProductsByCategory(String category);
}
