package com.application.catalogue.Controller;
import com.application.catalogue.Product.Product;
import com.application.catalogue.Service.ProductServicePublic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


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
    public ResponseEntity<String> createProducts(@RequestBody Product product)
    {
        productServicePublic.createProduct(product);
        return  new ResponseEntity<>("Product added successfully" , HttpStatus.OK);
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



//    @GetMapping("/product/{article}/image")
//    public ResponseEntity<InputStreamResource> getProductImage(@PathVariable String article) {
//        Product product = productServicePublic.findByArticle(article);
//        if (product == null || product.getImages() == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        // Retrieve the image bytes using the OID
//        byte[] imageBytes = getImageFromDatabase(product.getImages()); // Implement this method
//        if (imageBytes == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        ByteArrayInputStream imageStream = new ByteArrayInputStream(imageBytes);
//        return ResponseEntity.ok()
//                .contentType(MediaType.IMAGE_JPEG) // Adjust based on image type
//                .body(new InputStreamResource(imageStream));
//    }

//    // Implement a method to retrieve the image bytes using OID
//    private byte[] getImageFromDatabase(Long oid) {
//        // Logic to retrieve the image bytes from the database using the OID
//        return new byte[0]; // Replace with actual byte array
//    }




}
