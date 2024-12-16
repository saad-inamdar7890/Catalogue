// src/main/java/com/application/catalogue/Service/ProductServicePublicImpl.java
package com.application.catalogue.Service;

import com.application.catalogue.Product.Image;
import com.application.catalogue.Product.Product;
import com.application.catalogue.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServicePublicImpl implements ProductServicePublic {

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
    public String deleteProduct(String article) {
        Product removeProduct = productRepo.findById(article).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
        productRepo.delete(removeProduct);
        return "Product with Article: " + article + " is deleted successfully!";
    }

    @Override
    public Product updateProduct(Product product, String article) {
        Optional<Product> savedProductOptional = productRepo.findById(article);
        Product savedProduct = savedProductOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
        product.setArticle(article);
        savedProduct = productRepo.save(product);
        return savedProduct;
    }

    @Override
    public List<Product> getProductsRegisteredWithin7Days() {
        LocalDateTime daysAgo = LocalDateTime.now().minusDays(7);
        return productRepo.findByDefinedDateAfter(daysAgo);
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepo.findByCategory(category);
    }

    @Override
    public List<Product> getProductsByGender(String gender) {
        return productRepo.findByGender(gender);
    }

    @Override
    public Product findByArticle(String article) {
        return productRepo .findByArticle(article);
    }

    @Override
    public List<Product> getProductsByGenderAndCategory(String gender, String category) {
        return productRepo.findByGenderAndCategory(gender.toLowerCase(), category.toLowerCase());
    }

    @Override
    public List<Product> searchProducts(String brand, String article) {
        return productRepo.findByBrandContainingIgnoreCaseOrArticleContainingIgnoreCase(brand, article);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepo.findByBrand(brand);
    }

    @Override
    public List<String> searchBrands(String brand) {
        return productRepo.findDistinctBrandByBrandContainingIgnoreCase(brand);
    }

    @Override
    public List<String> searchArticles(String article) {
        return productRepo.findDistinctArticleByArticleContainingIgnoreCase(article);
    }

    @Override
    public void deleteProductWithImage(String article) {

    }

//    @Override
//    public void deleteProductWithImage(String article) {
//        Product product = findByArticle(article);
//        if (product != null) {
//            // Delete the image files
//            for (Image image : product.getImages()) {
//                File imageFile = new File(image.getImagePath());
//                if (imageFile.exists()) {
//                    imageFile.delete();
//                }
//            }
//            // Delete the product
//            deleteProduct(article);
//        }
//    }

    @Override
    public void updateProductWithImage(Product product, String article, MultipartFile image) throws IOException {
        Product existingProduct = findByArticle(article);
        if (existingProduct != null) {
            // Delete the old image file if a new image is provided
            if (image != null && !image.isEmpty()) {
                File oldImageFile = new File(existingProduct.getImagePath());
                if (oldImageFile.exists()) {
                    oldImageFile.delete();
                }
                // Save the new image
                String directoryPath = new File("src/main/resources/static/images/").getAbsolutePath();
                File directory = new File(directoryPath);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                String imagePath = directoryPath + File.separator + image.getOriginalFilename();
                File imageFile = new File(imagePath);
                image.transferTo(imageFile);
                product.setImagePath(imagePath);
            }
            // Update the product
            updateProduct(product, article);
        }
    }
}