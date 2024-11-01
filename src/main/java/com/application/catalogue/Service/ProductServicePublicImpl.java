package com.application.catalogue.Service;

import com.application.catalogue.Product.Product;
import com.application.catalogue.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServicePublicImpl implements ProductServicePublic{

    @Autowired
    private ProductRepo productRepo;

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public void createProduct(Product product) {
        productRepo.save(product);
    }

    @Override
    public String deleteProduct(String Article) {

        Product removeProduct = productRepo.findById(Article).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));

        productRepo.delete(removeProduct);
        return "Product with Article : "+Article+" is deleted successfully !!";
    }

    @Override
    public Product updateProduct(Product product,  String Article) {

        Optional<Product> savedProductOptional = productRepo.findById(Article);
        Product savedcategory = savedProductOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
        product.setArticle(Article);
        savedcategory = productRepo.save(product);
        return savedcategory;

    }

    // @Override
    // public List<Product> getTrends() {
    //     return productRepo.findByTrendTrue();
    // }

    public List<Product> getProductsRegisteredWithin7Days() {
        LocalDateTime DaysAgo = LocalDateTime.now().minusDays(7);
        return productRepo.findByDefinedDateAfter(DaysAgo);
    }


    public List<Product> getProductsByCategory(String category) {
        return productRepo.findByCategory(category);
    }

    @Override
    public List<Product> getProductsByGender(String gender) {
        return productRepo.findByGender(gender);
    }

    @Override
    public Product findByArticle(String article) {
        return productRepo.findByArticle(article);
    }

    @Override
    public List<Product> getProductsByGenderAndCategory(String gender, String category) {
        return productRepo.findByGenderAndCategory( gender, category);
    }


}
