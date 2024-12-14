// src/main/java/com/application/catalogue/DTO/ImageWithColorDTO.java
package com.application.catalogue.DTO;

public class ImageWithColorDTO {
    private String imagePath;
    private String colorName;

    public ImageWithColorDTO(String imagePath, String colorName) {
        this.imagePath = imagePath;
        this.colorName = colorName;
    }

    // Getters and setters
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }
}