package com.application.catalogue.Controller;
import com.application.catalogue.Product.Product;
import com.application.catalogue.Service.ProductServicePublic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    @Autowired
    private ProductServicePublic productServicePublic;



    @GetMapping("/api/public/home")
    public ResponseEntity<List<Product>> getAllProduct()
    {
        return  new ResponseEntity<>(productServicePublic.getAllProducts() , HttpStatus.OK);
    }




    @PostMapping("/api/public/product")
    public ResponseEntity<String> createProducts(@RequestParam Map<String, String> productParams, @RequestParam("image") MultipartFile image) throws JsonProcessingException {
        Product product = new Product();
        product.setArticle(productParams.get("article"));
        product.setBrand(productParams.get("brand"));
       // product.setColour(new ObjectMapper().readValue(productParams.get("colour"), List.class)); // Convert JSON string to List
        product.setRate((float) Double.parseDouble(productParams.get("rate")));
        product.setSizeRange(new ObjectMapper().readValue(productParams.get("size_range"), List.class)); // Convert JSON string to List
        product.setGender(productParams.get("gender"));
        product.setBundleSize(Integer.parseInt(productParams.get("bundle_size")));
        product.setTrend(Boolean.parseBoolean(productParams.get("trend")));
        product.setDefinedDate(LocalDate.parse(productParams.get("defined_date")).atStartOfDay());
        product.setCategory(productParams.get("category"));
        
        // Handle image saving logic here if needed

        productServicePublic.createProduct(product);
        return new ResponseEntity<>("Product added successfully", HttpStatus.OK);
    }




    @DeleteMapping("/api/admin/product/{Article}")
    public ResponseEntity<String> deleteCategory(@PathVariable String Article)
    {
        try
        {
            String status = productServicePublic.deleteProduct(Article);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (ResponseStatusException e)
        {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }




    //@RequestMapping(value = "/public/categories/{categoryId}" , method = RequestMethod.PUT)
    @PutMapping("/api/public/products/{Article}")
    public ResponseEntity<String> updateProduct(@RequestBody Product product , @PathVariable String Article) {
        try {
            productServicePublic.updateProduct(product, Article); // Removed unused variable
            return new ResponseEntity<>("Updated Category with Id : " + Article, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }



    @GetMapping("/api/public/products/by-category")
    public ResponseEntity<List<Product>> getProductsByCategory(@RequestParam String category) {
        return   new ResponseEntity<>(productServicePublic.getProductsByCategory(category), HttpStatus.OK);
    }

    @GetMapping("/api/public/products/by-gender")
    public ResponseEntity<List<Product>> getProductsBGender(@RequestParam String gender) {
        return   new ResponseEntity<>(productServicePublic.getProductsByGender(gender), HttpStatus.OK);
    }

    @GetMapping("/api/public/products/by-gender-and-category")
    public ResponseEntity<List<Product>> getProductsBGenderAndCategory(@RequestParam String gender, @RequestParam String category) {
        return new ResponseEntity<>(productServicePublic.getProductsByGenderAndCategory(gender, category), HttpStatus.OK);
    }



}