package com.application.catalogue.Controller.admin;

import com.application.catalogue.Product.Image;
import com.application.catalogue.Product.Product;
import com.application.catalogue.Service.ImageService;
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

        if ("admin".equals(username) && "jas123".equals(password)) {
            // Simulate token generation
            Map<String, String> response = Map.of("token", "dummy-token");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/products/save")
    public ResponseEntity<String> saveProduct(@RequestParam Map<String, String> productParams,
                                              @RequestParam("image") MultipartFile image) throws IOException {
        // Save image
        String directoryPath = new File("src/main/resources/static/images/").getAbsolutePath();
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String imagePath = directoryPath + File.separator + image.getOriginalFilename();
        File imageFile = new File(imagePath);
        image.transferTo(imageFile);

        // Create product
        Product product = new Product();
        product.setArticle(productParams.get("article"));
        product.setColour(productParams.get("colour"));
        product.setBrand(productParams.get("brand"));
        product.setRate(Float.parseFloat(productParams.get("rate")));
        product.setSizeRange(String.valueOf(new ObjectMapper().readValue(productParams.get("size_range"), new TypeReference<List<String>>() {})));
        product.setGender(productParams.get("gender"));
        product.setBundleSize(Integer.parseInt(productParams.get("bundle_size")));
        product.setTrend(Boolean.parseBoolean(productParams.get("trend")));
        product.setDefinedDate(LocalDate.parse(productParams.get("defined_date")).atStartOfDay());
        product.setCategory(productParams.get("category"));
        product.setImagePath(imagePath);

        // Save product
        productServicePublic.createProduct(product);

        return new ResponseEntity<>("Product saved successfully", HttpStatus.OK);
    }
}