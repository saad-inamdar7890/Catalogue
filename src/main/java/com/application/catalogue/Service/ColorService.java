// src/main/java/com/application/catalogue/Service/ColorService.java
package com.application.catalogue.Service;

import com.application.catalogue.Product.Color;
import com.application.catalogue.Repository.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColorService {

    @Autowired
    private ColorRepository colorRepository;

    public List<Color> getAllColors() {
        return colorRepository.findAll();
    }

    public Color getColorById(Long id) {
        Optional<Color> color = colorRepository.findById(id);
        return color.orElse(null);
    }

    public Color createColor(Color color) {
        return colorRepository.save(color);
    }

    public Color updateColor(Long id, Color color) {
        if (colorRepository.existsById(id)) {
            color.setId(id);
            return colorRepository.save(color);
        } else {
            return null;
        }
    }

    public boolean deleteColor(Long id) {
        if (colorRepository.existsById(id)) {
            colorRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}