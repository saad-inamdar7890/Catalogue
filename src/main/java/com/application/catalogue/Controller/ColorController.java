// src/main/java/com/application/catalogue/Controller/ColorController.java
package com.application.catalogue.Controller;

import com.application.catalogue.Product.Color;
import com.application.catalogue.Service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/public/colors")
public class ColorController {

    @Autowired
    private ColorService colorService;

    @GetMapping
    public ResponseEntity<List<Color>> getAllColors() {
        return new ResponseEntity<>(colorService.getAllColors(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Color> getColorById(@PathVariable Long id) {
        Color color = colorService.getColorById(id);
        return new ResponseEntity<>(color, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Color> createColor(@RequestBody Color color) {
        Color createdColor = colorService.createColor(color);
        return new ResponseEntity<>(createdColor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Color> updateColor(@PathVariable Long id, @RequestBody Color color) {
        Color updatedColor = colorService.updateColor(id, color);
        return new ResponseEntity<>(updatedColor, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColor(@PathVariable Long id) {
        boolean isDeleted = colorService.deleteColor(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/by-product/{article}")
    public ResponseEntity<List<Color>> getColorsByProductArticle(@PathVariable String article) {
        List<Color> colors = colorService.getColorsByProductArticle(article);
        return new ResponseEntity<>(colors, HttpStatus.OK);
    }

    @PostMapping("/by-product/{article}")
    public ResponseEntity<Color> createColorByProductArticle(@PathVariable String article, @RequestBody Color color) {
        Color createdColor = colorService.createColorByProductArticle(article, color);
        return new ResponseEntity<>(createdColor, HttpStatus.CREATED);
    }

    @PutMapping("/by-product/{article}/{colorId}")
    public ResponseEntity<Color> updateColorByProductArticle(@PathVariable String article, @PathVariable Long colorId, @RequestBody Color color) {
        Color updatedColor = colorService.updateColorByProductArticle(article, colorId, color);
        return new ResponseEntity<>(updatedColor, HttpStatus.OK);
    }

    @DeleteMapping("/by-product/{article}/{colorId}")
    public ResponseEntity<Void> deleteColorByProductArticle(@PathVariable String article, @PathVariable Long colorId) {
        boolean isDeleted = colorService.deleteColorByProductArticle(article, colorId);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}