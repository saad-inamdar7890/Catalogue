package com.application.catalogue.Service;

import com.application.catalogue.Product.Product;
import com.application.catalogue.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
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
        Product product = findByArticle(article);
        if (product != null) {
            // Delete the image file
            File imageFile = new File(product.getImagePath());
            if (imageFile.exists()) {
                imageFile.delete();
            }
            // Delete the product
            deleteProduct(article);
        }
    }


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
 