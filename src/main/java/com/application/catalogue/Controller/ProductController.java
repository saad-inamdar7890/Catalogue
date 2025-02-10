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

    @GetMapping("/api/public/products/registered/within-7-days")
    public ResponseEntity<List<Product>> getProductsRegisteredWithin7Days() {
        List<Product> products = productServicePublic.getProductsRegisteredWithin7Days();
        return new ResponseEntity<>(products, HttpStatus.OK);
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

    @GetMapping("/{article}/{colour}")
    public ResponseEntity<Product> getProductByArticleAndColour(@PathVariable String article, @PathVariable String colour) {
        Product product = productServicePublic.findByArticleAndColour(article, colour);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/search/articles")
    public ResponseEntity<List<String>> searchArticles(@RequestParam String article) {
        if (article == null || article.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<String> articles = productServicePublic.searchArticles(article);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/search/brands")
    public ResponseEntity<List<String>> searchBrands(@RequestParam String brand) {
        if (brand == null || brand.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<String> brands = productServicePublic.searchBrands(brand);
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }

    @GetMapping("/brands")
    public ResponseEntity<List<String>> getAllBrands() {
        List<String> brands = productServicePublic.getAllBrands();
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }

    @GetMapping("/by-brand/{brandName}")
    public ResponseEntity<List<Product>> getProductsByBrandName(@PathVariable String brandName) {
        List<Product> products = productServicePublic.getProductsByBrand(brandName);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}