package com.application.catalogue.Repository;

import com.application.catalogue.Product.Product;
import com.application.catalogue.Product.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, ProductId> {
    Product findByArticle(String article);
    List<Product> findByCategory(String category);
    List<Product> findByGender(String gender);
    List<Product> findByGenderAndCategory(String gender, String category);
    List<Product> findByBrandContainingIgnoreCaseOrArticleContainingIgnoreCase(String brand, String article);
    List<Product> findByBrand(String brand);
    List<Product> findByDefinedDateAfter(LocalDateTime date);

    List<String> findDistinctBrandByBrandContainingIgnoreCase(String brand);

    List<String> findDistinctArticleByArticleContainingIgnoreCase(String article);

    List<Product> findByBrandContainingIgnoreCase(String brand);
    List<Product> findByArticleContainingIgnoreCase(String article);

    @Query("DELETE FROM Product p WHERE p.article = :article AND p.colour = :colour")
    void deleteProduct(@Param("article") String article, @Param("colour") String colour);

    Product findByArticleAndColour(String article, String colour);
}