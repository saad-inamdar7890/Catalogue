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


    @PutMapping("/products/update")
    public ResponseEntity<String> updateProduct(
            @RequestParam Map<String, String> productParams,
            @RequestParam("defaultImage") MultipartFile defaultImage,
            @RequestParam(value = "images", required = false) MultipartFile[] images,
            @RequestParam(value = "replaceImageIds", required = false) Map<Long, MultipartFile> replaceImageIds,
            @RequestParam(value = "removeImageIds", required = false) List<Long> removeImageIds) {
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
            if (removeImageIds != null) {
                for (Long imageId : removeImageIds) {
                    imageService.deleteImageById(imageId);
                }
            }

            // Replace specified images
            if (replaceImageIds != null) {
                for (Map.Entry<Long, MultipartFile> entry : replaceImageIds.entrySet()) {
                    Long imageId = entry.getKey();
                    MultipartFile newImage = entry.getValue();
                    Image oldImage = imageService.getImageById(imageId);
                    if (oldImage != null) {
                        // Delete the old image from the Google Cloud Storage bucket
                        googleCloudStorageService.deleteFile(oldImage.getImagePath());
                        // Upload the new image
                        String newImageUrl = googleCloudStorageService.uploadFile(newImage, bucketName);
                        // Update the image record
                        oldImage.setImagePath(newImageUrl);
                        imageService.saveImage(oldImage);
                    }
                }
            }

            // Save additional images
            if (images != null) {
                for (MultipartFile image : images) {
                    String imageUrl = googleCloudStorageService.uploadFile(image, bucketName);

                    Image img = new Image();
                    img.setImagePath(imageUrl); // Save the GCS URL
                    img.setProduct(product);
                    imageService.saveImage(img);
                }
            }

            return new ResponseEntity<>("Product and images updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while updating the product and images", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/products/saveWithImages")
    public ResponseEntity<String> saveProductWithImages(
            @RequestParam Map<String, String> productParams,
            @RequestParam("defaultImage") MultipartFile defaultImage,
            @RequestParam(value = "images", required = false) MultipartFile[] images) {
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
            if (images != null) {
                for (MultipartFile image : images) {
                    String imageUrl = googleCloudStorageService.uploadFile(image, bucketName);

                    Image img = new Image();
                    img.setImagePath(imageUrl); // Save the GCS URL
                    img.setProduct(product);
                    imageService.saveImage(img);
                }
            }

            return new ResponseEntity<>("Product and images saved successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while saving the product and images", HttpStatus.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity<String> saveBrand(
            @RequestParam("name") String name,
            @RequestParam("image") MultipartFile image) {
        try {
            String bucketName = System.getenv("GCS_BUCKET_NAME"); // Ensure this environment variable is set
            String imageUrl = googleCloudStorageService.uploadFile(image, bucketName);

            Brand brand = new Brand();
            brand.setName(name);
            brand.setImagePath(imageUrl); // Save the GCS URL

            brandService.createBrand(brand);
            return new ResponseEntity<>("Brand saved successfully", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while saving the brand", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/brands/update/{id}")
    public ResponseEntity<String> updateBrand(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            Brand brand = brandService.getBrandById(id).orElseThrow(() -> new RuntimeException("Brand not found"));

            brand.setName(name);

            if (image != null && !image.isEmpty()) {
                String bucketName = System.getenv("GCS_BUCKET_NAME"); // Ensure this environment variable is set
                String imageUrl = googleCloudStorageService.uploadFile(image, bucketName);
                brand.setImagePath(imageUrl); // Update the GCS URL
            }

            brandService.updateBrand(id, brand);
            return new ResponseEntity<>("Brand updated successfully", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while updating the brand", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/brands/delete/{id}")
    public ResponseEntity<String> deleteBrand(@PathVariable Long id) {
        try {
            Brand brand = brandService.getBrandById(id).orElseThrow(() -> new RuntimeException("Brand not found"));

            // Delete the image from Google Cloud Storage
            googleCloudStorageService.deleteFile(brand.getImagePath());

            // Delete the brand from the database
            brandService.deleteBrand(id);

            return new ResponseEntity<>("Brand deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while deleting the brand", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}