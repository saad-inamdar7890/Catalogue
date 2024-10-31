package com.application.catalogue.Product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="Product")
public class Product {

    @Id
    private String article;
    private String brand;

    @ElementCollection
    @Column(name = "colour")
    private List<String> colour = new ArrayList<>();

    private float rate;

    @ElementCollection
    @Column(name = "size_range")
    private List<String> sizeRange = new ArrayList<>();

    private String gender;
    private int bundleSize;


    @Column(name = "trend")
    private boolean trend;

    @Column(name = "defined_date")
    private LocalDateTime definedDate;

    private String category;

    @PrePersist
    protected void onCreate() {
        this.definedDate = LocalDateTime.now();
    }


}
