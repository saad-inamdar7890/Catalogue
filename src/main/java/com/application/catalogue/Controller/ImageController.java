package com.application.catalogue.Controller;

import com.application.catalogue.Product.Image;
import com.application.catalogue.Service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/public/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping
    public ResponseEntity<List<Image>> getAllImages() {
        return new ResponseEntity<>(imageService.getAllImages(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Image> getImageById(@PathVariable Long id) {
        Image image = imageService.getImageById(id);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Image> createImage(@RequestBody Image image) {
        Image createdImage = imageService.createImage(image);
        return new ResponseEntity<>(createdImage, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Image> updateImage(@PathVariable Long id, @RequestBody Image image) {
        Image updatedImage = imageService.updateImage(id, image);
        return new ResponseEntity<>(updatedImage, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        boolean isDeleted = imageService.deleteImage(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/by-product/{article}/{colour}")
    public ResponseEntity<List<String>> getImagesByProductArticle(@PathVariable String article, @PathVariable String colour) {
        List<Image> images = imageService.getImagesByProductArticle(article, colour);
        List<String> imagePaths = images.stream().map(Image::getImagePath).toList();
        return new ResponseEntity<>(imagePaths, HttpStatus.OK);
    }

    @PostMapping("/by-product/{article}/{colour}")
    public ResponseEntity<Image> createImageByProductArticle(@PathVariable String article, @PathVariable String colour, @RequestBody Image image) {
        Image createdImage = imageService.createImageByProductArticle(article, image);
        return new ResponseEntity<>(createdImage, HttpStatus.CREATED);
    }

    @PutMapping("/by-product/{article}/{colour}/{imageId}")
    public ResponseEntity<Image> updateImageByProductArticle(@PathVariable String article, @PathVariable String colour, @PathVariable Long imageId, @RequestBody Image image) {
        Image updatedImage = imageService.updateImageByProductArticle(article,  imageId, image);
        return new ResponseEntity<>(updatedImage, HttpStatus.OK);
    }

    @DeleteMapping("/by-product/{article}/{imageId}")
    public ResponseEntity<Void> deleteImageByProductArticle(@PathVariable String article, @PathVariable String colour, @PathVariable Long imageId) {
        boolean isDeleted = imageService.deleteImageByProductArticle(article, imageId);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}