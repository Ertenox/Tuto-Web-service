package com.imt.framework.web.tuto.resources;

import com.imt.framework.web.tuto.entities.Livre;
import com.imt.framework.web.tuto.repositories.LivreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class LivreResourceTest {

    @Mock
    private LivreRepository livreRepository;

    @InjectMocks
    private LivreResource livreResource;

    private Livre livre;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        livre = new Livre();
        livre.setId(1);
        livre.setTitre("Test Livre");
        livre.setAuteur("Test Auteur");
        livre.setPrice(20.0);
    }

    @Test
    public void testCreateBook() {
        // Simule la sauvegarde d'un livre
        when(livreRepository.save(livre)).thenReturn(livre);

        livreResource.createBook(livre);

        verify(livreRepository, times(1)).save(livre);
    }

    @Test
    public void testUpdateBookNotFound() {
        // Simule que le livre à mettre à jour n'existe pas
        when(livreRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            livreResource.updateBook(1, livre);
        });

        assertEquals("Livre inconnu", exception.getMessage());
        verify(livreRepository, times(1)).findById(1);
    }

    @Test
    public void testUpdateBookSuccess() throws Exception {
        // Simule que le livre à mettre à jour existe
        when(livreRepository.findById(1)).thenReturn(Optional.of(livre));

        livre.setTitre("Updated Livre");
        livre.setAuteur("Updated Auteur");
        livre.setPrice(25.0);

        livreResource.updateBook(1, livre);

        verify(livreRepository, times(1)).save(livre);
    }

    @Test
    public void testDeleteBook() {
        // Simule la suppression d'un livre
        doNothing().when(livreRepository).deleteById(1);

        livreResource.deleteBook(1);

        verify(livreRepository, times(2)).deleteById(1);
    }
}
