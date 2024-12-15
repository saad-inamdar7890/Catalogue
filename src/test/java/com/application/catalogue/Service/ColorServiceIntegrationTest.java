// src/test/java/com/application/catalogue/Service/ColorServiceIntegrationTest.java
package com.application.catalogue.Service;

import com.application.catalogue.Product.Color;
import com.application.catalogue.Repository.ColorRepository;
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
class ColorServiceIntegrationTest {

    @Autowired
    private ColorService colorService;

    @Autowired
    private ColorRepository colorRepository;

    @BeforeEach
    void setUp() {
        colorRepository.deleteAll();
    }

    @Test
    void testGetAllColors() {
        Color color1 = new Color();
        Color color2 = new Color();
        colorRepository.save(color1);
        colorRepository.save(color2);

        List<Color> colors = colorService.getAllColors();
        assertEquals(2, colors.size());
    }

    @Test
    void testGetColorById() {
        Color color = new Color();
        color = colorRepository.save(color);

        Color foundColor = colorService.getColorById(color.getId());
        assertNotNull(foundColor);
    }

    @Test
    void testCreateColor() {
        Color color = new Color();
        Color createdColor = colorService.createColor(color);
        assertNotNull(createdColor);
        assertNotNull(createdColor.getId());
    }

    @Test
    void testUpdateColor() {
        Color color = new Color();
        color = colorRepository.save(color);
        color.setName("Updated Name");

        Color updatedColor = colorService.updateColor(color.getId(), color);
        assertNotNull(updatedColor);
        assertEquals("Updated Name", updatedColor.getName());
    }

    @Test
    void testDeleteColor() {
        Color color = new Color();
        color = colorRepository.save(color);

        boolean isDeleted = colorService.deleteColor(color.getId());
        assertTrue(isDeleted);
        assertFalse(colorRepository.existsById(color.getId()));
    }
}