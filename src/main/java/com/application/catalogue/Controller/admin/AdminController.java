package com.application.catalogue.Controller.admin;

import com.application.catalogue.Product.Brand;
import com.application.catalogue.Product.Image;
import com.application.catalogue.Product.Product;
import com.application.catalogue.Service.BrandService;
import com.application.catalogue.Service.GoogleCloudStorageService;
import com.application.catalogue.Service.ImageService;
import com.application.catalogue.Service.ProductServicePublic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "${frontend.url}")
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private ProductServicePublic productServicePublic;

    @Autowired
    private ImageService imageService;


    @Autowired
    private BrandService brandService;

    @Autowired
    private GoogleCloudStorageService googleCloudStorageService;

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



    @PostMapping("/products/saveWithImages")
    public ResponseEntity<String> saveProductWithImages(
            @RequestParam Map<String, String> productParams,
            @RequestParam("defaultImage") MultipartFile defaultImage,
            @RequestParam("images") MultipartFile[] images) {
        try {
            String bucketName = System.getenv("GCS_BUCKET_NAME"); // Ensure this environment variable is set
            String defaultImageUrl = googleCloudStorageService.uploadFile(defaultImage, bucketName);

            // Create and save product
            Product product = new Product();
            product.setArticle(productParams.get("article"));
            product.setColour(productParams.get("colour"));
            product.setBrand(productParams.get("brand"));
            product.setRate(Float.parseFloat(productParams.get("rate")));
            product.setSizeRange(productParams.get("size_range"));
            product.setGender(productParams.get("gender"));
            product.setBundleSize(Integer.parseInt(productParams.get("bundle_size")));
            product.setStock(Boolean.parseBoolean(productParams.get("stock"))); // Changed from trend
            product.setCategory(productParams.get("category"));
            product.setImagePath(defaultImageUrl); // Save the GCS URL

            productServicePublic.createProduct(product);

            // Save additional images
            for (MultipartFile image : images) {
                String imageUrl = googleCloudStorageService.uploadFile(image, bucketName);

                Image img = new Image();
                img.setImagePath(imageUrl); // Save the GCS URL
                img.setProduct(product);
                imageService.saveImage(img);
            }

            return new ResponseEntity<>("Product and images saved successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while saving the product and images", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/products/update")
    public ResponseEntity<String> updateProduct(
            @RequestParam Map<String, String> productParams,
            @RequestParam("defaultImage") MultipartFile defaultImage,
            @RequestParam("images") MultipartFile[] images,
            @RequestParam("removeImageIds") List<Long> removeImageIds) {
        try {
            String bucketName = System.getenv("GCS_BUCKET_NAME"); // Ensure this environment variable is set

            // Update default image if provided
            String defaultImageUrl = null;
            if (defaultImage != null && !defaultImage.isEmpty()) {
                defaultImageUrl = googleCloudStorageService.uploadFile(defaultImage, bucketName);
            }

            // Create and update product
            Product product = new Product();
            product.setArticle(productParams.get("article"));
            product.setColour(productParams.get("colour"));
            product.setBrand(productParams.get("brand"));
            product.setRate(Float.parseFloat(productParams.get("rate")));
            product.setSizeRange(productParams.get("size_range"));
            product.setGender(productParams.get("gender"));
            product.setBundleSize(Integer.parseInt(productParams.get("bundle_size")));
            product.setStock(Boolean.parseBoolean(productParams.get("stock")));
            product.setCategory(productParams.get("category"));
            if (defaultImageUrl != null) {
                product.setImagePath(defaultImageUrl); // Save the GCS URL
            }

            productServicePublic.updateProduct(product, productParams.get("article"), productParams.get("colour"));

            // Remove specified images
            for (Long imageId : removeImageIds) {
                imageService.deleteImageById(imageId);
            }

            // Save additional images
            for (MultipartFile image : images) {
                String imageUrl = googleCloudStorageService.uploadFile(image, bucketName);

                Image img = new Image();
                img.setImagePath(imageUrl); // Save the GCS URL
                img.setProduct(product);
                imageService.saveImage(img);
            }

            return new ResponseEntity<>("Product and images updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while updating the product and images", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/products/delete")
    public ResponseEntity<String> deleteProduct(@RequestParam String article, @RequestParam String colour) {
        try {
            productServicePublic.deleteProductWithImages(article, colour);
            return new ResponseEntity<>("Product and associated images deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while deleting the product and images", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/brands/save")
    public ResponseEntity<String> saveBrand(@RequestBody Brand brand) {
        try {
            brandService.createBrand(brand);
            return new ResponseEntity<>("Brand saved successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while saving the brand", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/brands/update/{id}")
    public ResponseEntity<String> updateBrand(@PathVariable Long id, @RequestBody Brand brandDetails) {
        try {
            brandService.updateBrand(id, brandDetails);
            return new ResponseEntity<>("Brand updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while updating the brand", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/brands/delete/{id}")
    public ResponseEntity<String> deleteBrand(@PathVariable Long id) {
        try {
            brandService.deleteBrand(id);
            return new ResponseEntity<>("Brand deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while deleting the brand", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}