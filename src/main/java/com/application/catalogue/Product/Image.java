package com.application.catalogue.Product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imagePath;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "product_article", referencedColumnName = "article"),
            @JoinColumn(name = "product_colour", referencedColumnName = "colour")
    })
    private Product product;
}