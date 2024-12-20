package com.application.catalogue.Controller.admin;

import com.application.catalogue.Product.Color;
import com.application.catalogue.Product.Image;
import com.application.catalogue.Product.Product;
import com.application.catalogue.Service.ColorService;
import com.application.catalogue.Service.ImageService;
import com.application.catalogue.Service.ProductServicePublic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private ProductServicePublic productServicePublic;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ColorService colorService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if ("admin".equals(username) && "jas123".equals(password)) {
            // Simulate token generation
            Map<String, String> response = Map.of("token", "dummy-token");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    // Product endpoints
//    @PostMapping("/products")
//    public ResponseEntity<String> createProduct(@RequestParam Map<String, String> productParams, @RequestParam("image") MultipartFile image) {
//        return productServicePublic.createProduct(productParams, image);
//    }
//
//    @PutMapping("/products/{article}")
//    public ResponseEntity<String> updateProduct(@RequestParam Map<String, String> productParams, @RequestParam("image") MultipartFile image, @PathVariable String article) {
//        return productServicePublic.updateProduct(productParams, image, article);
//    }
//
//    @DeleteMapping("/products/{article}")
//    public ResponseEntity<String> deleteProduct(@PathVariable String article) {
//        return productServicePublic.deleteProduct(article);
//    }

//    // Image endpoints
//    @PostMapping("/products/{article}/images")
//    public ResponseEntity<Image> createImageByProductArticle(@PathVariable String article, @RequestBody Image image) {
//        return new ResponseEntity<>(imageService.createImageByProductArticle(article, image), HttpStatus.CREATED);
//    }

    @PutMapping("/products/{article}/images/{imageId}")
    public ResponseEntity<Image> updateImageByProductArticle(@PathVariable String article, @PathVariable Long imageId, @RequestBody Image image) {
        return new ResponseEntity<>(imageService.updateImageByProductArticle(article, imageId, image), HttpStatus.OK);
    }

    @DeleteMapping("/products/{article}/images/{imageId}")
    public ResponseEntity<Void> deleteImageByProductArticle(@PathVariable String article, @PathVariable Long imageId) {
        boolean isDeleted = imageService.deleteImageByProductArticle(article, imageId);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Color endpoints
    @PostMapping("/products/{article}/colors")
    public ResponseEntity<Color> createColorByProductArticle(@PathVariable String article, @RequestBody Color color) {
        return new ResponseEntity<>(colorService.createColorByProductArticle(article, color), HttpStatus.CREATED);
    }

    @PutMapping("/products/{article}/colors/{colorId}")
    public ResponseEntity<Color> updateColorByProductArticle(@PathVariable String article, @PathVariable Long colorId, @RequestBody Color color) {
        return new ResponseEntity<>(colorService.updateColorByProductArticle(article, colorId, color), HttpStatus.OK);
    }

//    @DeleteMapping("/products/{article}/colors/{colorId}")
//    public ResponseEntity<Void> deleteColorByProductArticle(@PathVariable String article, @PathVariable Long colorId) {
//        boolean isDeleted = colorService.deleteColorByProductArticle(article, colorId);
//        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
}