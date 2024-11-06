package com.application.catalogue.Repository;

import com.application.catalogue.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepo extends JpaRepository<Product, String> {

    //List<Product> findByTrendTrue();

    List<Product> findByDefinedDateAfter(LocalDateTime date);


    List<Product> findByCategory(String category);


    List<Product> findByGender(String gender);

    Product findByArticle(String article);


    List<Product> findByGenderAndCategory(String gender, String category);

    List<Product> findByBrandContainingIgnoreCaseOrArticleContainingIgnoreCase(String brand, String article);
}
