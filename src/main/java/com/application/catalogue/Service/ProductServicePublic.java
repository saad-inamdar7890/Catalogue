// src/main/java/com/application/catalogue/Service/ProductServicePublic.java
package com.application.catalogue.Service;

import com.application.catalogue.Product.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductServicePublic {
    List<Product> getAllProducts();
    void createProduct(Product product);
    String deleteProduct(String article);
    Product updateProduct(Product product, String article);
    List<Product> getProductsRegisteredWithin7Days();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByGender(String gender);
    Product findByArticle(String article);
    List<Product> getProductsByGenderAndCategory(String gender, String category);
    List<Product> searchProducts(String brand, String article);
    List<Product> getProductsByBrand(String brand);
    List<String> searchBrands(String brand);
    List<String> searchArticles(String article);
    void deleteProductWithImage(String article);
    void updateProductWithImage(Product product, String article, MultipartFile image) throws IOException;
}