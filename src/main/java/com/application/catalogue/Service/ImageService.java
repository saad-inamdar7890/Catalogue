// src/main/java/com/application/catalogue/Service/ImageService.java
package com.application.catalogue.Service;

import com.application.catalogue.Product.Image;
import com.application.catalogue.Repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    public Image getImageById(Long id) {
        Optional<Image> image = imageRepository.findById(id);
        return image.orElse(null);
    }

    @Transactional
    public Image createImage(Image image) {
        return imageRepository.save(image);
    }

    @Transactional
    public Image updateImage(Long id, Image image) {
        if (imageRepository.existsById(id)) {
            image.setId(id);
            return imageRepository.save(image);
        } else {
            return null;
        }
    }

    @Transactional
    public boolean deleteImage(Long id) {
        if (imageRepository.existsById(id)) {
            imageRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}