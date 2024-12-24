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
    Product updateProduct(Product product, String article, String colour);
    Product findByArticle(String article);
    List<Product> getProductsByGenderAndCategory(String gender, String category);
    List<Product> searchProducts(String brand, String article);
    List<Product> getProductsByBrand(String brand);
    List<String> searchBrands(String brand);
    List<String> searchArticles(String article);
    void deleteProductWithImage(String article, String colour);
    void updateProductWithImage(Product product, String article, MultipartFile image) throws IOException;
    List<String> getAllBrands();
    Product findByArticleAndColour(String article, String colour);
}
