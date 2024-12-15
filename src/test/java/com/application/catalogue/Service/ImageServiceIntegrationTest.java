// src/test/java/com/application/catalogue/Service/ImageServiceIntegrationTest.java
package com.application.catalogue.Service;

import com.application.catalogue.Product.Image;
import com.application.catalogue.Repository.ImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ImageServiceIntegrationTest {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageRepository imageRepository;

    @BeforeEach
    void setUp() {
        imageRepository.deleteAll();
    }

    @Test
    void testGetAllImages() {
        Image image1 = new Image();
        Image image2 = new Image();
        imageRepository.save(image1);
        imageRepository.save(image2);

        List<Image> images = imageService.getAllImages();
        assertEquals(2, images.size());
    }

    @Test
    void testGetImageById() {
        Image image = new Image();
        image = imageRepository.save(image);

        Image foundImage = imageService.getImageById(image.getId());
        assertNotNull(foundImage);
    }

    @Test
    void testCreateImage() {
        Image image = new Image();
        Image createdImage = imageService.createImage(image);
        assertNotNull(createdImage);
        assertNotNull(createdImage.getId());
    }

    @Test
    void testUpdateImage() {
        Image image = new Image();
        image = imageRepository.save(image);
        image.setImagePath("Updated URL");

        Image updatedImage = imageService.updateImage(image.getId(), image);
        assertNotNull(updatedImage);
        assertEquals("Updated URL", updatedImage.getImagePath());
    }

    @Test
    void testDeleteImage() {
        Image image = new Image();
        image = imageRepository.save(image);

        boolean isDeleted = imageService.deleteImage(image.getId());
        assertTrue(isDeleted);
        assertFalse(imageRepository.existsById(image.getId()));
    }
}