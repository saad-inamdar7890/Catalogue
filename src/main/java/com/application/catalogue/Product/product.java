// src/main/java/com/application/catalogue/Product/Product.java
package com.application.catalogue.Product;

import com.application.catalogue.Product.Image;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Product")
public class Product {

    @Id
    private String article;
    private String brand;
    private float rate;
    private String sizeRange;
    private String gender;
    private int bundleSize;
    private boolean trend;
    private LocalDateTime definedDate;
    private String category;
    private String imagePath;

//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Color> colours;
//
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Image> images;

    @PrePersist
    protected void onCreate() {
        this.definedDate = LocalDateTime.now();
    }
}