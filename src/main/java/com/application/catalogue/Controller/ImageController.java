package com.application.catalogue.Controller;

import com.application.catalogue.Product.Image;
import com.application.catalogue.Service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/public/images")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    public ResponseEntity<List<Image>> getAllImages() {
        return new ResponseEntity<>(imageService.getAllImages(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Image> getImageById(@PathVariable Long id) {
        Image image = imageService.getImageById(id);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @PostMapping("/by-product/{article}/{colour}")
    public ResponseEntity<Image> createImageByProductArticle(@PathVariable String article, @PathVariable String colour, @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            Image createdImage = imageService.createImageByProductArticle(article, colour, imageFile);
            return new ResponseEntity<>(createdImage, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/by-product/{article}/{colour}/{imageId}")
    public ResponseEntity<Image> updateImageByProductArticle(@PathVariable String article, @PathVariable String colour, @PathVariable Long imageId, @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            Image updatedImage = imageService.updateImageByProductArticle(article, colour, imageFile);
            return new ResponseEntity<>(updatedImage, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/by-product/{article}/{colour}/{imageId}")
    public ResponseEntity<Void> deleteImageByProductArticle(@PathVariable String article, @PathVariable String colour, @PathVariable Long imageId) {
        boolean isDeleted = imageService.deleteImageByProductArticle(article, colour, imageId);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/by-product/{article}/{colour}")
    public ResponseEntity<List<String>> getImagesByProductArticle(@PathVariable String article, @PathVariable String colour) {
        List<Image> images = imageService.getImagesByProductArticle(article, colour);
        List<String> imagePaths = images.stream().map(Image::getImagePath).toList();
        return new ResponseEntity<>(imagePaths, HttpStatus.OK);
    }
}