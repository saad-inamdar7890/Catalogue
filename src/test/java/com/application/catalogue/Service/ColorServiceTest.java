// src/test/java/com/application/catalogue/Service/ColorServiceTest.java
package com.application.catalogue.Service;

import com.application.catalogue.Product.Color;
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

class ColorServiceTest {

    @Mock
    private ColorRepository colorRepository;

    @InjectMocks
    private ColorService colorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllColors() {
        Color color1 = new Color();
        Color color2 = new Color();
        when(colorRepository.findAll()).thenReturn(Arrays.asList(color1, color2));

        List<Color> colors = colorService.getAllColors();
        assertEquals(2, colors.size());
        verify(colorRepository, times(1)).findAll();
    }

    @Test
    void testGetColorById() {
        Color color = new Color();
        when(colorRepository.findById(1L)).thenReturn(Optional.of(color));

        Color foundColor = colorService.getColorById(1L);
        assertNotNull(foundColor);
        verify(colorRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateColor() {
        Color color = new Color();
        when(colorRepository.save(color)).thenReturn(color);

        Color createdColor = colorService.createColor(color);
        assertNotNull(createdColor);
        verify(colorRepository, times(1)).save(color);
    }

    @Test
    void testUpdateColor() {
        Color color = new Color();
        when(colorRepository.existsById(1L)).thenReturn(true);
        when(colorRepository.save(color)).thenReturn(color);

        Color updatedColor = colorService.updateColor(1L, color);
        assertNotNull(updatedColor);
        verify(colorRepository, times(1)).existsById(1L);
        verify(colorRepository, times(1)).save(color);
    }

    @Test
    void testDeleteColor() {
        when(colorRepository.existsById(1L)).thenReturn(true);

        boolean isDeleted = colorService.deleteColor(1L);
        assertTrue(isDeleted);
        verify(colorRepository, times(1)).existsById(1L);
        verify(colorRepository, times(1)).deleteById(1L);
    }
}