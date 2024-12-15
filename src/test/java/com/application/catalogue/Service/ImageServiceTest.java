// src/test/java/com/application/catalogue/Service/ImageServiceTest.java
package com.application.catalogue.Service;

import com.application.catalogue.Product.Image;
import com.application.catalogue.Repository.ImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImageServiceTest {

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageService imageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllImages() {
        Image image1 = new Image();
        Image image2 = new Image();
        when(imageRepository.findAll()).thenReturn(Arrays.asList(image1, image2));

        List<Image> images = imageService.getAllImages();
        assertEquals(2, images.size());
        verify(imageRepository, times(1)).findAll();
    }

    @Test
    void testGetImageById() {
        Image image = new Image();
        when(imageRepository.findById(1L)).thenReturn(Optional.of(image));

        Image foundImage = imageService.getImageById(1L);
        assertNotNull(foundImage);
        verify(imageRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateImage() {
        Image image = new Image();
        when(imageRepository.save(image)).thenReturn(image);

        Image createdImage = imageService.createImage(image);
        assertNotNull(createdImage);
        verify(imageRepository, times(1)).save(image);
    }

    @Test
    void testUpdateImage() {
        Image image = new Image();
        when(imageRepository.existsById(1L)).thenReturn(true);
        when(imageRepository.save(image)).thenReturn(image);

        Image updatedImage = imageService.updateImage(1L, image);
        assertNotNull(updatedImage);
        verify(imageRepository, times(1)).existsById(1L);
        verify(imageRepository, times(1)).save(image);
    }

    @Test
    void testDeleteImage() {
        when(imageRepository.existsById(1L)).thenReturn(true);

        boolean isDeleted = imageService.deleteImage(1L);
        assertTrue(isDeleted);
        verify(imageRepository, times(1)).existsById(1L);
        verify(imageRepository, times(1)).deleteById(1L);
    }
}