package com.application.catalogue.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    private String article;
    private String brand;
    private String colour;
    private float rate;
    private List<String> sizeRange;
    private String gender;
    private int bundleSize;
    private List<BufferedImage> images = new ArrayList<>();

    // Constructors, Getters, and Setters

    public Product() {}

    public Product(String article, String brand, String colour, float rate, List<String> sizeRange, String gender, int bundleSize, List<BufferedImage> images) {
        this.article = article;
        this.brand = brand;
        this.colour = colour;
        this.rate = rate;
        this.sizeRange = sizeRange;
        this.gender = gender;
        this.bundleSize = bundleSize;
        this.images = images;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public List<String> getSizeRange() {
        return sizeRange;
    }

    public void setSizeRange(List<String> sizeRange) {
        this.sizeRange = sizeRange;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getBundleSize() {
        return bundleSize;
    }

    public void setBundleSize(int bundleSize) {
        this.bundleSize = bundleSize;
    }

    public List<BufferedImage> getImages() {
        return images;
    }

    public void setImages(List<BufferedImage> images) {
        this.images = images;
    }

    public void addImage(BufferedImage image) {
        this.images.add(image);
    }

    public void removeImage(BufferedImage image) {
        this.images.remove(image);
    }
}
