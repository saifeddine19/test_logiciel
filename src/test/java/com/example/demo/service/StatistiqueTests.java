package com.example.demo.service;

import com.example.demo.data.Voiture;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;


@SpringBootTest
public class StatistiqueTests {

    @Test
    void testPrixMoyen() {

        // Instance réelle pour tester
        StatistiqueImpl statistique = new StatistiqueImpl();

        // Création des mocks
        Voiture voitureMock1 =mock(Voiture.class);
        Voiture voitureMock2 =mock(Voiture.class);

        // On donne le comportement aux mocks
        when(voitureMock1.getPrix()).thenReturn(5000);
        when(voitureMock2.getPrix()).thenReturn(15000);

        // On les ajoute à notre instance de statistique
        statistique.ajouter(voitureMock1);
        statistique.ajouter(voitureMock2);

        Echantillon resultat = statistique.prixMoyen();

        // On vérifie les résultats
        assertEquals(2, resultat.getNombreDeVoitures());
        assertEquals(10000, resultat.getPrixMoyen());
    }

}
