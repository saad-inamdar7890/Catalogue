package com.application.catalogue.Service;

import com.application.catalogue.Product.Image;
import com.application.catalogue.Product.Product;
import com.application.catalogue.Repository.ImageRepository;
import com.application.catalogue.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ImageService {
    @Autowired
    private final ProductRepo productRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private GoogleCloudStorageService googleCloudStorageService;


    public ImageService(ImageRepository imageRepository, ProductRepo productRepository) {
        this.imageRepository = imageRepository;
        this.productRepository = productRepository;
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElse(null);
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
    public Image createImageByProductArticle(String article, String colour, MultipartFile imageFile) throws IOException {
        Product product = productRepository.findByArticleAndColour(article, colour);
        if (product != null) {
            String directoryPath = "/app/images/"; // Railway server directory
            Path directory = Paths.get(directoryPath);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            String imagePath = directoryPath + imageFile.getOriginalFilename();
            Path filePath = Paths.get(imagePath);
            Files.copy(imageFile.getInputStream(), filePath);

            Image image = new Image();
            image.setProduct(product);
            image.setImagePath(imagePath);
            return imageRepository.save(image);
        }
        return null;
    }

    @Transactional
    public Image updateImageByProductArticle(String article, String colour, MultipartFile imageFile) throws IOException {
        Product product = productRepository.findByArticleAndColour(article, colour);
        if (product != null) {
            List<Image> images = imageRepository.findByProductArticleAndProductColour(article, colour);
            if (!images.isEmpty()) {
                Image image = images.get(0);
                String oldImagePath = image.getImagePath();
                Path oldImageFile = Paths.get(oldImagePath);
                if (Files.exists(oldImageFile)) {
                    Files.delete(oldImageFile);
                }

                String directoryPath = "/app/images/"; // Railway server directory
                Path directory = Paths.get(directoryPath);
                if (!Files.exists(directory)) {
                    Files.createDirectories(directory);
                }

                String imagePath = directoryPath + imageFile.getOriginalFilename();
                Path filePath = Paths.get(imagePath);
                Files.copy(imageFile.getInputStream(), filePath);

                image.setImagePath(imagePath);
                return imageRepository.save(image);
            }
        }
        return null;
    }

    @Transactional
    public boolean deleteImageByProductArticle(String article, String colour, Long imageId) throws IOException {
        Image image = imageRepository.findById(imageId).orElse(null);
        if (image != null && image.getProduct().getArticle().equals(article) && image.getProduct().getColour().equals(colour)) {
            String imagePath = image.getImagePath();
            Path imageFile = Paths.get(imagePath);
            if (Files.exists(imageFile)) {
                Files.delete(imageFile);
            }
            imageRepository.delete(image);
            return true;
        }
        return false;
    }

    @Transactional
    public void deleteImageById(Long id) throws IOException {
        Image image = imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));
        // Delete the image from the Google Cloud Storage bucket
        googleCloudStorageService.deleteFile(image.getImagePath());
        imageRepository.deleteById(id);
    }

    @Transactional
    public void saveImage(Image img) {
        imageRepository.save(img);
    }

}