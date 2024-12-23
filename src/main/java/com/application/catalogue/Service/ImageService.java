package com.application.catalogue.Service;

import com.application.catalogue.Product.Image;
import com.application.catalogue.Product.Product;
import com.application.catalogue.Repository.ImageRepository;
import com.application.catalogue.Repository.ProductRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final ProductRepo productRepository;

    public ImageService(ImageRepository imageRepository, ProductRepo productRepository) {
        this.imageRepository = imageRepository;
        this.productRepository = productRepository;
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

    public List<Image> getImagesByProductArticle(String article, String colour) {
        return imageRepository.findByProductArticleAndProductColour(article, colour);
    }

    @Transactional
    public Image createImageByProductArticle(String article, Image image) {
        Product product = productRepository.findByArticle(article);
        image.setProduct(product);
        return imageRepository.save(image);
    }

    @Transactional
    public Image updateImageByProductArticle(String article, Long imageId, Image image) {
        Product product = productRepository.findByArticle(article);
        image.setId(imageId);
        image.setProduct(product);
        return imageRepository.save(image);
    }

    @Transactional
    public boolean deleteImageByProductArticle(String article, Long imageId) {
        if (imageRepository.existsById(imageId)) {
            imageRepository.deleteById(imageId);
            return true;
        }
        return false;
    }
}