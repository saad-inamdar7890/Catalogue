// src/main/java/com/application/catalogue/Controller/ProductController.java
package com.application.catalogue.Controller;

import com.application.catalogue.Product.Product;
import com.application.catalogue.Service.ProductServicePublic;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/public/products")
public class ProductController {

    @Autowired
    private ProductServicePublic productServicePublic;

    @GetMapping("/home")
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productServicePublic.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{article}")
    public ResponseEntity<Product> getProductByArticle(@PathVariable String article) {
        Product product = productServicePublic.findByArticle(article);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestParam Map<String, String> productParams, @RequestParam("image") MultipartFile image) {
        try {
            String directoryPath = new File("src/main/resources/static/images/").getAbsolutePath();
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String imagePath = directoryPath + File.separator + image.getOriginalFilename();
            File imageFile = new File(imagePath);
            image.transferTo(imageFile);

            Product product = new Product();
            product.setArticle(productParams.get("article"));
            product.setBrand(productParams.get("brand"));
            product.setRate(Float.parseFloat(productParams.get("rate")));
            product.setSizeRange(String.valueOf(new ObjectMapper().readValue(productParams.get("size_range"), new TypeReference<List<String>>() {})));
            product.setGender(productParams.get("gender"));
            product.setBundleSize(Integer.parseInt(productParams.get("bundle_size")));
            product.setTrend(Boolean.parseBoolean(productParams.get("trend")));
            product.setDefinedDate(LocalDate.parse(productParams.get("defined_date")).atStartOfDay());
            product.setCategory(productParams.get("category"));
            product.setImagePath(imagePath);

            productServicePublic.createProduct(product);
            return new ResponseEntity<>("Product added successfully", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error saving image", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{article}")
    public ResponseEntity<String> updateProduct(@RequestParam Map<String, String> productParams, @RequestParam("image") MultipartFile image, @PathVariable String article) {
        try {
            Product product = new Product();
            product.setArticle(productParams.get("article"));
            product.setBrand(productParams.get("brand"));
            product.setRate(Float.parseFloat(productParams.get("rate")));
            product.setSizeRange(String.valueOf(new ObjectMapper().readValue(productParams.get("size_range"), new TypeReference<List<String>>() {})));
            product.setGender(productParams.get("gender"));
            product.setBundleSize(Integer.parseInt(productParams.get("bundle_size")));
            product.setTrend(Boolean.parseBoolean(productParams.get("trend")));
            product.setDefinedDate(LocalDate.parse(productParams.get("defined_date")).atStartOfDay());
            product.setCategory(productParams.get("category"));

            productServicePublic.updateProductWithImage(product, article, image);
            return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error saving image", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{article}")
    public ResponseEntity<String> deleteProduct(@PathVariable String article) {
        try {
            productServicePublic.deleteProductWithImage(article);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/by-gender-and-category")
    public ResponseEntity<List<Product>> getProductsByGenderAndCategory(@RequestParam String gender, @RequestParam String category) {
        List<Product> products = productServicePublic.getProductsByGenderAndCategory(gender, category);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/by-brand")
    public ResponseEntity<List<Product>> getProductsByBrand(@RequestParam String brand) {
        List<Product> products = productServicePublic.getProductsByBrand(brand);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}