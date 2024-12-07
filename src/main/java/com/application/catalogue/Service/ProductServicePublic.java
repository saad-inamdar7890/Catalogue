package com.application.catalogue.Service;

import com.application.catalogue.Product.Product;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ProductServicePublic {
    List<Product> getAllProducts();
    void createProduct(Product product);

    String deleteProduct(String Article);

    Product updateProduct(Product product, String Article);

    // List<Product> getTrends();

    public List<Product> getProductsRegisteredWithin7Days();


    public List<Product> getProductsByCategory(String category);

    public  List<Product> getProductsByGender(String gender);

    Product findByArticle(String article);

    List<Product> getProductsByGenderAndCategory(String gender, String category);

    List<Product> searchProducts(String brand, String article);

    List<Product> getProductsByBrand(String brand);

    List<String> searchBrands(String brand);

    List<String> searchArticles(String article);

    void deleteProductWithImage(String article);

    void updateProductWithImage(Product product, String article, MultipartFile image) throws IOException;

}
