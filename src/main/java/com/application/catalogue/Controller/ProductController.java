package com.application.catalogue.Controller;
import com.application.catalogue.Product.Image;
import com.application.catalogue.Product.Product;
import com.application.catalogue.Service.ProductServicePublic;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



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
public ResponseEntity<String> createProducts(@RequestParam Map<String, String> productParams, @RequestParam("image") MultipartFile image) {
    try {
        // Define the directory and create it if it doesn't exist
        String directoryPath = new File("src/main/resources/static/images/").getAbsolutePath();
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Save the image to the directory
        String imagePath = directoryPath + File.separator + image.getOriginalFilename();
        File imageFile = new File(imagePath);
        image.transferTo(imageFile);

        // Create and save the product
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
    } catch (JsonProcessingException e) {
        e.printStackTrace();
        return new ResponseEntity<>("Error parsing JSON", HttpStatus.BAD_REQUEST);
    } catch (IOException e) {
        e.printStackTrace();
        return new ResponseEntity<>("Error saving image", HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>("Error creating product", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

//    @DeleteMapping("/api/admin/product/{Article}")
//    public ResponseEntity<String> deleteCategory(@PathVariable String Article)
//    {
//        try
//        {
//            String status = productServicePublic.deleteProduct(Article);
//            return new ResponseEntity<>(status, HttpStatus.OK);
//        } catch (ResponseStatusException e)
//        {
//            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
//        }
//    }




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

    @GetMapping("/api/public/product/{article}")
    public ResponseEntity<Product> getProductByArticle(@PathVariable String article) {
        Product product = productServicePublic.findByArticle(article);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/api/public/search/brands")
    public ResponseEntity<List<String>> searchProducts(@RequestParam(required = false) String brand) {
        List<String> brands = productServicePublic.searchBrands(brand);
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }

    @GetMapping("/api/public/products/registered/within-7-days")
    public ResponseEntity<List<Product>> getProductsRegisteredWithin7Days() {
        List<Product> products = productServicePublic.getProductsRegisteredWithin7Days();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/api/public/products/trends")
    public ResponseEntity<List<Product>> getTrends() {
        List<Product> products = productServicePublic.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("/api/admin/product/{article}")
    public ResponseEntity<String> deleteProduct(@PathVariable String article) {
        try {
            productServicePublic.deleteProductWithImage(article);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/api/public/products/{article}")
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
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Error parsing JSON", HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity<>("Error saving image", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
  @GetMapping("/api/public/search/articles")
    public ResponseEntity<List<String>> searchArticles(@RequestParam(required = false) String article) {
        if (article == null || article.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<String> articles = productServicePublic.searchArticles(article);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/api/public/products/by-brand")
    public ResponseEntity<List<Product>> getProductsByBrand(@RequestParam String brand) {
        List<Product> products = productServicePublic.getProductsByBrand(brand);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @PostMapping("/api/admin/login")
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


    @PostMapping("/api/public/productWithImages")
    public ResponseEntity<String> createProducts(@RequestParam Map<String, String> productParams, @RequestParam("images") MultipartFile[] images) {
        try {
            // Define the directory and create it if it doesn't exist
            String directoryPath = new File("src/main/resources/static/images/").getAbsolutePath();
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Create and save the product
            Product product = new Product();
            product.setArticle(productParams.get("article"));
            product.setBrand(productParams.get("brand"));
            product.setRate(Float.parseFloat(productParams.get("rate")));
            product.setSizeRange(String.valueOf(new ObjectMapper().readValue(productParams.get("size_range"), (TypeReference<List<String>>) new TypeReference<List<String>>() {})));
            product.setGender(productParams.get("gender"));
            product.setBundleSize(Integer.parseInt(productParams.get("bundle_size")));
            product.setTrend(Boolean.parseBoolean(productParams.get("trend")));
            product.setDefinedDate(LocalDate.parse(productParams.get("defined_date")).atStartOfDay());
            product.setCategory(productParams.get("category"));

            // Save the images and associate them with the product
            ArrayList<Image> imageList = new ArrayList<>();
            for (MultipartFile image : images) {
                String imagePath = directoryPath + File.separator + image.getOriginalFilename();
                File imageFile = new File(imagePath);
                image.transferTo(imageFile);

                Image img = new Image();
                img.setImagePath(imagePath);
                img.setProduct(product);
                imageList.add(img);
            }
            product.setImages(imageList);


            productServicePublic.createProduct(product);
            return new ResponseEntity<>("Product added successfully", HttpStatus.OK);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error parsing JSON", HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error saving image", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error creating product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/api/public/product/{article}/images")
    public ResponseEntity<List<Image>> getProductImagesByArticle(@PathVariable String article) {
        Product product = productServicePublic.findByArticle(article);
        if (product != null) {
            return new ResponseEntity<>(product.getImages(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}