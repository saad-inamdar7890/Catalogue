package com.application.catalogue.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
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
    private List<String> colour;
    private float rate;
    private List<String> sizeRange;
    private String gender;
    private int bundleSize;
    @Lob
    private List<BufferedImage> images = new ArrayList<>();
    private boolean Trend ;

    private LocalDateTime definedDate;

    // Constructors, Getters, and Setters

    @PrePersist
    protected void onCreate() {
        this.definedDate = LocalDateTime.now();
    }

    public Product() {}

    public Product(String article, String brand, List<String> colour, float rate, List<String> sizeRange, String gender, int bundleSize, List<BufferedImage> images, boolean Trend) {
        this.article = article;
        this.brand = brand;
        this.colour = colour;
        this.rate = rate;
        this.sizeRange = sizeRange;
        this.gender = gender;
        this.bundleSize = bundleSize;
        this.images = images;
        this.Trend = Trend;
    }

    public boolean isTrend() {
        return Trend;
    }

    public void setTrend(boolean trend) {
        Trend = trend;
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

    public List<String> getColour() {
        return colour;
    }

    public void setColour(List<String> colour) {
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
