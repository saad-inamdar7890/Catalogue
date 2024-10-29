package com.application.catalogue.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.ElementCollection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Column;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    private String article;
    private String brand;
    @ElementCollection
    private List<String> colour;
    private float rate;
    @ElementCollection
    private List<String> sizeRange;
    private String gender;
    private int bundleSize;
    @ElementCollection
    @Lob
    private List<byte[]> images = new ArrayList<>();
    @Column(name = "trend")
    private boolean Trend ;

    @Column(name = "defined_date")
    private LocalDateTime definedDate;

    private String category;

    // Constructors, Getters, and Setters

    @PrePersist
    protected void onCreate() {
        this.definedDate = LocalDateTime.now();
    }

    public Product(String article, String category, String brand, List<String> colour, float rate, List<String> sizeRange, String gender, int bundleSize, List<byte[]> images, boolean Trend) {
        this.article = article;
        this.brand = brand;
        this.colour = colour;
        this.rate = rate;
        this.sizeRange = sizeRange;
        this.gender = gender;
        this.bundleSize = bundleSize;
        this.images = images;
        this.Trend = Trend;
        this.category = category;
    }

    public LocalDateTime getDefinedDate() {
        return definedDate;
    }

    public void setDefinedDate(LocalDateTime definedDate) {
        this.definedDate = definedDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> images) {
        this.images = images;
    }

    public void addImage(byte[] image) {
        this.images.add(image);
    }

    public void removeImage(byte[] image) {
        this.images.remove(image);
    }
}
