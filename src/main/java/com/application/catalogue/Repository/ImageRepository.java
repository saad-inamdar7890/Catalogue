package com.application.catalogue.Repository;

import com.application.catalogue.Product.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProductArticleAndProductColour(String article, String colour);
}