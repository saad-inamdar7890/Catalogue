package com.application.catalogue.Repository;

import com.application.catalogue.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    List<Product> findByBrand(String brand);

    // List<String> findDistinctBrandByBrandContainingIgnoreCase(String brand);

    @Query("SELECT DISTINCT p.article FROM Product p WHERE LOWER(p.article) LIKE LOWER(CONCAT('%', :article, '%'))")
    List<String> findDistinctArticleByArticleContainingIgnoreCase(@Param("article") String article);

    @Query("SELECT DISTINCT p.brand FROM Product p WHERE LOWER(p.brand) LIKE LOWER(CONCAT('%', :brand, '%'))")
    List<String> findDistinctBrandByBrandContainingIgnoreCase(@Param("brand") String brand);
}
