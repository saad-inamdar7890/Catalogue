// src/main/java/com/application/catalogue/Service/ColorService.java
package com.application.catalogue.Service;

import com.application.catalogue.Product.Color;
import com.application.catalogue.Repository.ColorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ColorService {

    private final ColorRepository colorRepository;

    public ColorService(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    public List<Color> getAllColors() {
        return colorRepository.findAll();
    }

    public Color getColorById(Long id) {
        Optional<Color> color = colorRepository.findById(id);
        return color.orElse(null);
    }

    @Transactional
    public Color createColor(Color color) {
        return colorRepository.save(color);
    }

    @Transactional
    public Color updateColor(Long id, Color color) {
        if (colorRepository.existsById(id)) {
            color.setId(id);
            return colorRepository.save(color);
        } else {
            return null;
        }
    }

    @Transactional
    public boolean deleteColor(Long id) {
        if (colorRepository.existsById(id)) {
            colorRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Color> getColorsByProductArticle(String article) {
        return colorRepository.findByProductArticle(article);
    }

    @Override
    public Color createColorByProductArticle(String article, Color color) {
        Product product = productRepository.findByArticle(article);
        color.setProduct(product);
        return colorRepository.save(color);
    }

    @Override
    public Color updateColorByProductArticle(String article, Long colorId, Color color) {
        Product product = productRepository.findByArticle(article);
        color.setId(colorId);
        color.setProduct(product);
        return colorRepository.save(color);
    }

    @Override
    public boolean deleteColorByProductArticle(String article, Long colorId) {
        if (colorRepository.existsById(colorId)) {
            colorRepository.deleteById(colorId);
            return true;
        }
        return false;
    }
}