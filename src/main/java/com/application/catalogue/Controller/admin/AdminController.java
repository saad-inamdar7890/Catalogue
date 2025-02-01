package com.application.catalogue.Controller.admin;

import com.application.catalogue.Product.Image;
import com.application.catalogue.Product.Product;
import com.application.catalogue.Service.ImageService;
import com.application.catalogue.Service.ProductServicePublic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private ProductServicePublic productServicePublic;

    @Autowired
    private ImageService imageService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if ("admin0".equals(username) && "saad830".equals(password)) {
            Map<String, String> response = Map.of("token", "dummy-token");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/products/save")
    public ResponseEntity<String> saveProduct(@RequestParam Map<String, String> productParams,
                                              @RequestParam("image") MultipartFile image) throws IOException {
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
        product.setColour(productParams.get("colour"));
        product.setBrand(productParams.get("brand"));
        product.setRate(Float.parseFloat(productParams.get("rate")));
        product.setSizeRange(productParams.get("size_range"));
        product.setGender(productParams.get("gender"));
        product.setBundleSize(Integer.parseInt(productParams.get("bundle_size")));
        product.setTrend(Boolean.parseBoolean(productParams.get("trend")));
        product.setCategory(productParams.get("category"));
        product.setImagePath(imagePath);

        productServicePublic.createProduct(product);

        return new ResponseEntity<>("Product saved successfully", HttpStatus.OK);
    }

    @PutMapping("/products/{article}/{colour}")
    public ResponseEntity<String> updateProduct(@PathVariable String article, @PathVariable String colour, @RequestBody Product product) {
        productServicePublic.updateProduct(product, article, colour);
        return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
    }



    @PutMapping("/products/{article}/{colour}/image")
    public ResponseEntity<String> updateProductWithImage(@PathVariable String article, @PathVariable String colour, @RequestParam("image") MultipartFile image) throws IOException {
        Product product = productServicePublic.findByArticleAndColour(article, colour);
        if (product != null) {
            imageService.updateImageByProductArticle(article, colour, image);
            return new ResponseEntity<>("Product and image updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/products/{article}/{colour}")
    public ResponseEntity<String> deleteProduct(@PathVariable String article, @PathVariable String colour) {
        productServicePublic.deleteProductWithImage(article, colour);
        return new ResponseEntity<>("Product and associated image deleted successfully", HttpStatus.OK);
    }

    @PostMapping("/products/{article}/{colour}/image")
    public ResponseEntity<String> createImageByProductArticle(@PathVariable String article, @PathVariable String colour, @RequestParam("image") MultipartFile image) throws IOException {
        Product product = productServicePublic.findByArticleAndColour(article, colour);
        if (product != null) {
            imageService.createImageByProductArticle(article, colour, image);
            return new ResponseEntity<>("Image created successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }
}