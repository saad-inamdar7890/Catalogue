// src/main/java/com/application/catalogue/Repository/ColorRepository.java
package com.application.catalogue.Repository;

import com.application.catalogue.Product.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
}