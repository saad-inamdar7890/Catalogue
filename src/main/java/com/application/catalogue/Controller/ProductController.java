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
            Product savedCategory = productServicePublic.updateProduct(product, Article);
            return new ResponseEntity<>("Updated Category wit Id : " + Article, HttpStatus.OK);

        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }



    @GetMapping("/products/by-category")
    public ResponseEntity<List<Product>> getProductsByCategory(@RequestParam String category) {
        return   new ResponseEntity<>(productServicePublic.getProductsByCategory(category), HttpStatus.OK);
    }
}
