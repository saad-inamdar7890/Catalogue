package com.application.catalogue.Service;

import com.application.catalogue.Product.Product;
import com.application.catalogue.Product.ProductId;
import com.application.catalogue.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServicePublicImpl implements ProductServicePublic {

    @Autowired
    private ProductRepo productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public void createProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    @Transactional
    public String deleteProduct(String article) {
        ProductId productId = new ProductId(article, null);
        Product removeProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
        productRepository.delete(removeProduct);
        return "Product with Article: " + article + " is deleted successfully!";
    }

    @Override
    @Transactional
    public Product updateProduct(Product product, String article) {
        ProductId productId = new ProductId(article, product.getColour());
        Product savedProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
        product.setArticle(article);
        return productRepository.save(product);
    }

    @Override
    public List<Product> getProductsRegisteredWithin7Days() {
        LocalDateTime daysAgo = LocalDateTime.now().minusDays(7);
        return productRepository.findByDefinedDateAfter(daysAgo);
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> getProductsByGender(String gender) {
        return productRepository.findByGender(gender);
    }
    public Product findByArticleAndColour(String article, String colour) {
        return productRepository.findByArticleAndColour(article, colour);
    }
    @Override
    public Product findByArticle(String article) {
        return productRepository.findByArticle(article);
    }

    @Override
    public List<Product> getProductsByGenderAndCategory(String gender, String category) {
        return productRepository.findByGenderAndCategory(gender, category);
    }

    @Override
    public List<Product> searchProducts(String brand, String article) {
        return productRepository.findByBrandContainingIgnoreCaseOrArticleContainingIgnoreCase(brand, article);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<String> searchBrands(String brand) {
        return productRepository.findDistinctBrandByBrandContainingIgnoreCase(brand);
    }

    @Override
    public List<String> searchArticles(String article) {
        return productRepository.findDistinctArticleByArticleContainingIgnoreCase(article);
    }

    @Override
    @Transactional
    public void deleteProductWithImage(String article) {
        Product product = findByArticle(article);
        if (product != null) {
            File imageFile = new File(product.getImagePath());
            if (imageFile.exists()) {
                imageFile.delete();
            }
            deleteProduct(article);
        }
    }

    @Override
    @Transactional
    public void updateProductWithImage(Product product, String article, MultipartFile image) throws IOException {
        Product existingProduct = findByArticle(article);
        if (existingProduct != null) {
            if (image != null && !image.isEmpty()) {
                File oldImageFile = new File(existingProduct.getImagePath());
                if (oldImageFile.exists()) {
                    oldImageFile.delete();
                }
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
            updateProduct(product, article);
        }
    }
}