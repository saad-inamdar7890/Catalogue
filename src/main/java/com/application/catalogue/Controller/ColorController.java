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
        List<Color> colors = colorService.getAllColors();
        return new ResponseEntity<>(colors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Color> getColorById(@PathVariable Long id) {
        Color color = colorService.getColorById(id);
        if (color != null) {
            return new ResponseEntity<>(color, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Color> createColor(@RequestBody Color color) {
        Color createdColor = colorService.createColor(color);
        return new ResponseEntity<>(createdColor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Color> updateColor(@PathVariable Long id, @RequestBody Color color) {
        Color updatedColor = colorService.updateColor(id, color);
        if (updatedColor != null) {
            return new ResponseEntity<>(updatedColor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColor(@PathVariable Long id) {
        boolean isDeleted = colorService.deleteColor(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}