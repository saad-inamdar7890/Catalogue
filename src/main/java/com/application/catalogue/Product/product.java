// src/main/java/com/application/catalogue/Product/Product.java
package com.application.catalogue.Product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Product")
@IdClass(ProductId.class)
public class Product implements Serializable {

    @Id
    private String article;

    @Id
    private String colour;

    private String brand;
    private float rate;
    private String sizeRange;
    private String gender;
    private int bundleSize;
    private boolean trend;
    private LocalDateTime definedDate;
    private String category;
    private String imagePath;

    @PrePersist
    protected void onCreate() {
        this.definedDate = LocalDateTime.now();
    }
}