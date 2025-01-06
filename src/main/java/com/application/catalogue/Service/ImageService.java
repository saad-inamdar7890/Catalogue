package com.application.catalogue.Service;

import com.application.catalogue.Product.Image;
import com.application.catalogue.Product.Product;
import com.application.catalogue.Repository.ImageRepository;
import com.application.catalogue.Repository.ProductRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
            String directoryPath = new File("src/main/resources/static/images/").getAbsolutePath();
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String imagePath = directoryPath + File.separator + imageFile.getOriginalFilename();
            File file = new File(imagePath);
            imageFile.transferTo(file);

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
                File oldImageFile = new File(oldImagePath);
                if (oldImageFile.exists()) {
                    oldImageFile.delete();
                }

                String directoryPath = new File("src/main/resources/static/images/").getAbsolutePath();
                File directory = new File(directoryPath);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                String imagePath = directoryPath + File.separator + imageFile.getOriginalFilename();
                File file = new File(imagePath);
                imageFile.transferTo(file);

                image.setImagePath(imagePath);
                return imageRepository.save(image);
            }
        }
        return null;
    }

    @Transactional
    public boolean deleteImageByProductArticle(String article, String colour, Long imageId) {
        Image image = imageRepository.findById(imageId).orElse(null);
        if (image != null && image.getProduct().getArticle().equals(article) && image.getProduct().getColour().equals(colour)) {
            String imagePath = image.getImagePath();
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                imageFile.delete();
            }
            imageRepository.delete(image);
            return true;
        }
        return false;
    }
}