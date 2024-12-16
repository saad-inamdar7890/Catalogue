// src/main/java/com/application/catalogue/Service/ImageService.java
package com.application.catalogue.Service;

import com.application.catalogue.Product.Image;
import com.application.catalogue.Product.Product;
import com.application.catalogue.Repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.application.catalogue.Repository.ProductRepo;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final ProductRepo productRepo;


    public ImageService(ImageRepository imageRepository, ProductRepo productRepo) {
        this.imageRepository = imageRepository;
        this.productRepo = productRepo;

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





    public List<Image> getImagesByProductArticle(String article) {
        return imageRepository.findByProductArticle(article);
    }


    public Image createImageByProductArticle(String article, Image image) {
        Product product = productRepo.findByArticle(article);
        image.setProduct(product);
        return imageRepository.save(image);
    }


    public Image updateImageByProductArticle(String article, Long imageId, Image image) {
        Product product = productRepo.findByArticle(article);
        image.setId(imageId);
        image.setProduct(product);
        return imageRepository.save(image);
    }


    public boolean deleteImageByProductArticle(String article, Long imageId) {
        if (imageRepository.existsById(imageId)) {
            imageRepository.deleteById(imageId);
            return true;
        }
        return false;
    }
}